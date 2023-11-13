package com.spd.hardware;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by HanWenRui, Date on 2023/11/13.
 * TODO: 自定义线程池，添加了无限循环方法。方便串口一直发送数据和接收。
 * TODO: 使用示例 单个任务使用
 *
 * // 创建线程池实例
 * ConfigurableThreadPool threadPool = new ConfigurableThreadPool(
 *     5,  // 核心线程数
 *     10, // 最大线程数
 *     60, // 保持活动时间
 *     TimeUnit.SECONDS,
 *     100 // 队列容量
 * );
 *
 * // 创建一个任务
 * Runnable singleTask = () -> {
 *     System.out.println("这个任务正在运行在: " + Thread.currentThread().getName());
 *     // 这里是你的任务逻辑
 * };
 *
 * // 提交任务到线程池
 * threadPool.submitTask(singleTask);
 *
 * // 当不再需要线程池时，关闭它
 * // 注意：确保在关闭线程池之前，所有任务都已完成
 * threadPool.shutdown();
 *
 * TODO: 使用示例 无限循环
 * ConfigurableThreadPool threadPool = new ConfigurableThreadPool(
 *     5,  // 核心线程数
 *     10, // 最大线程数
 *     60, // 保持活动时间
 *     TimeUnit.SECONDS,
 *     100 // 队列容量
 * );
 *
 * // 开始无限循环任务
 * threadPool.startLoopingTask(() -> {
 *     // 无限循环执行的任务逻辑
 * });
 *
 * // 在某个时间点停止无限循环
 * threadPool.stopLoopingTask();
 *
 * // 关闭线程池
 * threadPool.shutdown();
 */

public class ConfigurableThreadPool {
    private final ExecutorService executorService;
    private final AtomicBoolean isLooping;

    /**
     * 构造一个可配置的线程池。
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程保持活动时间
     * @param unit            时间单位
     * @param queueCapacity   队列容量
     */
    public ConfigurableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueCapacity) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);
        ThreadFactory threadFactory = new CustomThreadFactory();
        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        isLooping = new AtomicBoolean(false);
    }

    /**
     * 提交一个任务到线程池。
     *
     * @param task 要执行的任务
     */
    public void submitTask(Runnable task) {
        executorService.submit(task);
    }

    /**
     * 开始无限循环执行一个任务。
     *
     * @param task 要循环执行的任务
     */
    public void startLoopingTask(Runnable task) {
        isLooping.set(true);
        executorService.submit(() -> {
            while (isLooping.get()) {
                task.run();
            }
        });
    }

    /**
     * 停止无限循环任务。
     */
    public void stopLoopingTask() {
        isLooping.set(false);
    }

    /**
     * 关闭线程池，等待任务执行完毕或超时。
     */
    public void shutdown() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 自定义线程工厂，用于创建新线程。
     */
    private static class CustomThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}

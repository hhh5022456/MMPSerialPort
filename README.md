# MMPSerialPort

[![](https://www.jitpack.io/v/hhh5022456/MMPSerialPort.svg)](https://www.jitpack.io/#hhh5022456/MMPSerialPort)
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

implementation 'com.github.hhh5022456:MMPSerialPort:v0.1.0'

```


初始化
```
private val mSerialManager = SerialManager()
```
打开串口，支持更多配置，可查看`MainActivity#serialPortFunc`
```
SerialConfig serialConfig = new SerialConfig();
serialConfig.setDevice("dev/ttyS0").setSpeed(BaudRateValue.B115200);
serialManager.open(serialConfig, this);
```
串口数据回调

```
override fun onDataReceived(bytes: ByteArray) {

    }
```


其他回调，可重写可不重写
```
/**
     * 打开串口成功
     *
     * @param device 串口路径信息
     */
    default void onOpenSuccess(File device) {

    }

    /**
     * 打开串口失败
     *
     * @param e 错误信息
     */
    default void onOpenFailed(Exception e) {

    }


    /**
     * 串口发送的数据
     *
     * @param bytes 已发送数据
     */
    default void onDataSend(byte[] bytes) {
    }
```

发送串口数据,支持直接发送byte数组

```
mSerialManager.sendHex("000001")
```

关闭串口

```
mSerialManager.close()
```
获取串口列表
```
mSerialManager.getSerialPorts()
```

上电gpio操作
```
mSerialManager.setGpioPowerPath()
```


## ConfigurableThreadPool 使用指南

`ConfigurableThreadPool` 是一个自定义的线程池实现，支持基本的任务提交和无限循环任务处理。下面是如何在您的项目中使用它的示例。

### 基本任务提交

首先，创建 `ConfigurableThreadPool` 的实例：

```java
ConfigurableThreadPool threadPool = new ConfigurableThreadPool(
    5,  // 核心线程数
    10, // 最大线程数
    60, // 线程保持活动时间
    TimeUnit.SECONDS,
    100 // 队列容量
);

然后，创建并提交一个任务：
Runnable singleTask = () -> {
    System.out.println("这个任务正在运行在: " + Thread.currentThread().getName());
    // 在这里添加您的任务逻辑
};

// 提交任务到线程池
threadPool.submitTask(singleTask);

最后，当不再需要线程池时，确保所有任务都已完成后关闭它：
threadPool.shutdown();



如果需要执行无限循环任务，可以按照以下步骤操作：

 threadPool.startLoopingTask(() -> {
    // 在这里添加无限循环执行的任务逻辑
});

在需要停止循环时：
threadPool.stopLoopingTask();

最后，当不再需要线程池 关闭它：
threadPool.shutdown();

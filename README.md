# SerialPort

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

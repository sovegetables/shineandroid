[![](https://jitpack.io/v/sovegetables/shineandroid.svg)](https://jitpack.io/#sovegetables/shineandroid)

## Logger
#### Dependencies
```gradle
    maven { url "https://jitpack.io" }
    implementation 'com.github.sovegetables.shineandroid:logger:0.1.1'
```
#### Usage
```kotlin
    Logger.d(TAG, "onCreate-----")  // 默认是android.util.Log.d()
```


```kotlin
    //自定义用法, 也可以添加多个Log Delegate
    class LogImpl: ILog(){
        override fun println(priority: LEVEL, tag: String?, msg: String?) {
            when(priority){
                LEVEL.VERBOSE -> {
                    // TODO: 2019/11/1
                }
                LEVEL.DEBUG -> {
                    // TODO: 2019/11/1
                }
                LEVEL.INFO -> {
                    // TODO: 2019/11/1
                }
                LEVEL.WARN -> {
                    // TODO: 2019/11/1
                }
                LEVEL.ERROR -> {
                    // TODO: 2019/11/1
                }
            }
        }
    }
    
    companion object {
        init {
            Logger.setDelegate(LogImpl())
            
            //或者
            val logImpl = Logger.Delegates()
            logImpl.addDelegate(LogImpl())
            logImpl.addDelegate(Logger.ANDROID)
            Logger.setDelegate(logImpl)
        }
    }
    
```
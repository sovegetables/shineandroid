[![](https://jitpack.io/v/sovegetables/shineandroid.svg)](https://jitpack.io/#sovegetables/shineandroid)
### TopNavBar
#### Dependencies
```gradle
    maven { url "https://jitpack.io" }
    implementation 'com.github.sovegetables.shineandroid:topnavbar:0.0.10'
```
#### Usage

```xml
<com.sovegetables.topnavbar.ActionBarView
        android:id="@+id/action_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

```kotlin
//Setup
val left = TopBarItem.Builder()
    .icon(R.drawable.ic_baseline_menu_24)
    .listener {
        toast("点击左边的Icon")
    }
    .build(this, 1)

val right1 = TopBarItem.Builder()
    .icon(R.drawable.ic_baseline_add_circle_24)
    .listener {
        toast("点击右边的添加Icon")
    }
    .build(this, 2)

val right2 = TopBarItem.Builder()
    .icon(R.drawable.ic_baseline_sms_24)
    .listener {
        toast("点击右边的消息Icon")
    }
    .visibility(TopBarItem.Visibility.GONE)
    .build(this, 3)

val rights = arrayListOf<TopBarItem>()
rights.add(right1)
rights.add(right2)

val topBar = TopBar.Builder()
    .title("标题1")
    .left(left)
    .right(right3)
    .titleColor(Color.BLACK)
    .topBarColorRes(R.color.colorPrimary)
    .build(this)
action_bar.setUpTopBar(topBar)


//Dynamic Change ActionBar
action_bar.topBarUpdater
    .title("更新标题1")
    .titleColorRes(R.color.colorAccent)
    .topBarColor(Color.BLACK)
    .update();
    
action_bar.findRightItemUpdaterById(3)
    .visibility(View.VISIBLE)
    .update()    
```

#### Support
- 中间Title或者左边Title
- ActionBar右边添加icon，最多支持3个
- 动态改变ActionBar的title, 背景颜色，右上角icon可见性等


### GsonHelper
#### Dependencies
```gradle
    maven { url "https://jitpack.io" }
    implementation 'com.github.sovegetables.shineandroid:gsonhelper:0.0.10'
```

#### Usage
```s
    Gson gson = sonHelper.commonGson();
```
#### Support
- 使用SkipSerialize注解,注解类的属性, 跳过json序列化与反序列化
- MistakeBooleanDeserializer提高Boolean反序列化容错, 类的属性是boolean，json的value确是int,也不会导致解析报错，0 -> false; 1 -> true
- MistakeIntDeserializer提高Integer反序列化容错, 类的属性是int, json的value确是boolean, 也不会导致解析报错, false -> 0; true -> 1
- MistakeListDeserializer提高List反序列化容错, 实质需要"array":[], 但是实质是"array":""或者"array":{},也不会导致解析报错


### Logger
#### Dependencies
```gradle
    maven { url "https://jitpack.io" }
    implementation 'com.github.sovegetables.shineandroid:logger:0.0.10'
```
#### Usage
```kotlin
    Logger.d(TAG, "onCreate-----")  // 默认是android.util.Log.d()
```


```text
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
        }
    }
    
    //或者
    
    companion object{
        init {
            val logImpl = Logger.Delegates()
            logImpl.addDelegate(LogImpl())
            logImpl.addDelegate(Logger.ANDROID)
            Logger.setDelegate(logImpl)
        }
    }
    
    
```


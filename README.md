[![](https://jitpack.io/v/sovegetables/shineandroid.svg)](https://jitpack.io/#sovegetables/shineandroid)

## [TopNavBar](./topnavbar)
- 中间Title或者左边Title, 左边带icon的Title
- ActionBar右边添加icon，最多支持3个
- 动态改变ActionBar的title, 背景颜色，右上角icon可见性等


## [GsonHelper](./gsonhelper)
- 使用SkipSerialize注解,注解类的属性, 跳过json序列化与反序列化
- MistakeBooleanDeserializer提高Boolean反序列化容错, 类的属性是boolean，json的value确是int,也不会导致解析报错，0 -> false; 1 -> true
- MistakeIntDeserializer提高Integer反序列化容错, 类的属性是int, json的value确是boolean, 也不会导致解析报错, false -> 0; true -> 1
- MistakeListDeserializer提高List反序列化容错, 实质需要"array":[], 但是实质是"array":""或者"array":{},也不会导致解析报错


## [Logger](./logger)
- Log Delegate, support add multi Logger

## [AbsActivity](./absactivity)
- 一个方便的BaseActivity，简单继承即可，不用改变原来的setContentView逻辑
- 支持顶部导航栏 -> [TopNavBar](./topnavbar)

## [Runtime Permission](./permission)
- Android M Runtime Permission
- Support Kotlin

[![](https://jitpack.io/v/sovegetables/shineandroid.svg)](https://jitpack.io/#sovegetables/shineandroid)


### GsonHelper
#### Dependencies
```gradle
    maven { url "https://jitpack.io" }

    implementation 'com.github.sovegetables.shineandroid:gsonhelper:0.0.1'
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
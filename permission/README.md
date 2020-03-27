[![](https://jitpack.io/v/sovegetables/shineandroid.svg)](https://jitpack.io/#sovegetables/shineandroid)


## Permissions
#### Dependencies
```gradle
    maven { url "https://jitpack.io"}
    implementation "com.github.sovegetables:permission:0.1.3"
```
#### Usage
```kotlin
//Permission Result
data class PermissionResult(var permission: String, var granted: Boolean, var shouldShowPermissionRationale: Boolean)
```
```java
Permissions.request(fragment, p, new OnPermissionResultListener() {
    @Override
    public void allGranted(@NotNull ArrayList<PermissionResult> grantedPermissions) {
    }

    @Override
    public void denied(@NotNull ArrayList<PermissionResult> grantedPermissions, @NotNull ArrayList<PermissionResult> deniedPermissions) {
    }
});

Permissions.request(activity, permissions, onPermissionResultListener)
```

如果你使用了kotlin, activity和fragment也有相应的扩展函数，可以直接调用
package com.sovegetables.kt

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.KeyguardManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes
import java.io.File

fun Context.toast(msg: String, long:Int = Toast.LENGTH_SHORT){
    ToastHelper.toast(this, msg, long)
}

fun Context.toUri(@DrawableRes drawableId:Int): Uri {
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + this.resources.getResourcePackageName(drawableId)
                + '/'.toString() + this.resources.getResourceTypeName(drawableId)
                + '/'.toString() + this.resources.getResourceEntryName(drawableId)
    )
}

fun Context.toStringUrl(@DrawableRes drawableId:Int): String {
    return (ContentResolver.SCHEME_ANDROID_RESOURCE +
            "://" + this.resources.getResourcePackageName(drawableId)
            + '/'.toString() + this.resources.getResourceTypeName(drawableId)
            + '/'.toString() + this.resources.getResourceEntryName(drawableId))
}

fun Context.processName(): String? {
    val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    var processInfos: List<ActivityManager.RunningAppProcessInfo>? = am.runningAppProcesses
    if (processInfos == null) {
        processInfos = am.runningAppProcesses
    }
    if (processInfos != null) {
        for (run in processInfos) {
            if (run.pid == android.os.Process.myPid()) {
                return run.processName
            }
        }
    }
    return null
}

/**
 * 判断mainactivity是否处于栈顶
 *
 * @return true在栈顶false不在栈顶
 */
fun Context.isOnTop(activityClass: Class<out Activity?>): Boolean {
    return try {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val name = manager.getRunningTasks(1)[0].topActivity!!.className
        name == activityClass.name
    } catch (e: SecurityException) {
        false
    }
}


fun Context.isBackground(): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses =
        activityManager.runningAppProcesses
    for (appProcess in appProcesses) {
        if (appProcess.processName == packageName) {
            return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND
        }
    }
    return false
}

fun Context.isSleeping(): Boolean {
    val kgMgr = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return kgMgr.inKeyguardRestrictedInputMode()
}

fun Context.inMainProcess(): Boolean {
    val packageName = this.packageName
    val processName = processName()
    return packageName == processName
}

fun Context.appVersionName(): String? {
    var version: String? = "0"
    try {
        version = packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return version
}


fun Context.appVersionCode(): Int {
    var version = 0
    try {
        version = packageManager.getPackageInfo(packageName, 0).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return version
}

fun Context.appLanguage(): String? {
    return resources?.configuration?.locale?.language
}

fun Context.isGpsEnabled(): Boolean {
    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

val SU_SEARCH_PATHS = arrayOf(
    "/system/bin/",
    "/system/xbin/",
    "/system/sbin/",
    "/sbin/",
    "/vendor/bin/"
)


fun isRooted(): Boolean {
    var file: File
    var isRoot = false
    for (suSearchPath in SU_SEARCH_PATHS) {
        file = File(suSearchPath + "su")
        if (file.isFile && file.exists()) {
            isRoot = true
            break
        }
    }
    return isRoot
}

fun isEmulator(): Boolean {
    return (Build.BRAND.contains("generic")
            || Build.DEVICE.contains("generic")
            || Build.PRODUCT.contains("sdk")
            || Build.HARDWARE.contains("goldfish")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.PRODUCT.contains("vbox86p")
            || Build.DEVICE.contains("vbox86p")
            || Build.HARDWARE.contains("vbox86"))
}


@SuppressLint("ShowToast")
object ToastHelper {
    private var mToast: Toast? = null

    fun toast(con: Context?, resId: Int,duration: Int = Toast.LENGTH_SHORT) {
        if (con == null) {
            return
        }
        toast(con, con.getString(resId), duration)
    }

    fun toast(con: Context?, text: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (con == null || text == null) {
            return
        }
        val context = con.applicationContext
        if (mToast == null) {
            mToast = Toast.makeText(context.applicationContext, text, Toast.LENGTH_LONG)
        } else {
            mToast!!.setText(text)
            mToast!!.duration = duration
        }
        mToast!!.show()
    }
}
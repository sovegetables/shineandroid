package com.sovegetables.kt

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes

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

fun Context.getProcessName(): String? {
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

@SuppressLint("ShowToast")
object ToastHelper {
    private var mToast: Toast? = null
    fun toast(con: Context?, resId: Int,duration: Int = Toast.LENGTH_SHORT) {
        if (con == null) {
            return
        }
        toast(
            con,
            con.getString(resId),
            duration
        )
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
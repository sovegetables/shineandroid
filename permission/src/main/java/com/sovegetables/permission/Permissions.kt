package com.sovegetables.permission

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Permissions {

    companion object{

        private var deniedHandler: DeniedHandler? = null

        @JvmStatic
        fun setDeniedHandler(handler: DeniedHandler){
            deniedHandler = handler
        }

        internal fun getDeniedHandler(): DeniedHandler?{
            return deniedHandler
        }

        @JvmStatic
        fun request(activity: Activity, permission: String, listener: OnPermissionResultListener?){
            request(activity, arrayOf(permission), listener)
        }

        @JvmStatic
        fun request(activity: Activity, permissions: Array<String>, listener: OnPermissionResultListener?){
            if(activity is FragmentActivity){
                PermissionFragment.requestPermissions(activity, permissions, listener)
            }else{
                PermissionCompatFragment.requestPermissions(activity, permissions, listener)
            }
        }

        @JvmStatic
        fun request(fragment: Fragment, permissions: Array<String>, listener: OnPermissionResultListener?){
            PermissionFragment.requestPermissions(fragment, permissions, listener)
        }

        @JvmStatic
        fun request(fragment: Fragment, permission: String, listener: OnPermissionResultListener?){
            request(fragment, arrayOf(permission), listener)
        }

        @JvmStatic
        fun request(fragment: android.app.Fragment, permissions: Array<String>, listener: OnPermissionResultListener?){
            PermissionCompatFragment.requestPermissions(fragment, permissions, listener)
        }

        @JvmStatic
        fun request(fragment: android.app.Fragment, permission: String, listener: OnPermissionResultListener?){
            request(fragment, arrayOf(permission), listener)
        }

    }
}

fun Activity.request(permissions: Array<String>, listener: OnPermissionResultListener?){
    Permissions.request(this, permissions, listener)
}

fun Activity.request(permission: String, listener: OnPermissionResultListener?){
    Permissions.request(this, permission, listener)
}

fun Fragment.request(permissions: Array<String>, listener: OnPermissionResultListener?){
    Permissions.request(this, permissions, listener)
}

fun Fragment.request(permission: String, listener: OnPermissionResultListener?){
    Permissions.request(this, permission, listener)
}

fun android.app.Fragment.request(permissions: Array<String>, listener: OnPermissionResultListener?){
    Permissions.request(this, permissions, listener)
}

fun android.app.Fragment.request(permission: String, listener: OnPermissionResultListener?){
    Permissions.request(this, permission, listener)
}


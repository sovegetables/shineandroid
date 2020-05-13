package com.sovegetables.permission

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Permissions {

    companion object{

        @JvmStatic
        fun request(activity: Activity, permission: String, listener: OnPermissionResultListener?){
            request(activity, arrayOf(permission), listener)
        }

        @JvmStatic
        fun request(activity: Activity, permissions: Array<String>, listener: OnPermissionResultListener?){
            if(activity is FragmentActivity){
                PermissionFragment.injectIfNeededIn(activity)
                val permissionFragment = PermissionFragment[activity]
                permissionFragment.requestPermissions(permissions, listener)
            }else{
                PermissionFragment2.injectIfNeededIn(activity)
                val permissionFragment = PermissionFragment2[activity]
                permissionFragment.requestPermissions(permissions, listener)
            }
        }

        @JvmStatic
        fun request(fragment: Fragment, permissions: Array<String>, listener: OnPermissionResultListener?){
            PermissionFragment.injectIfNeededIn(fragment)
            val permissionFragment = PermissionFragment[fragment]
            permissionFragment.requestPermissions(permissions, listener)
        }

        @JvmStatic
        fun request(fragment: Fragment, permission: String, listener: OnPermissionResultListener?){
            request(fragment, arrayOf(permission), listener)
        }

        @JvmStatic
        fun request(fragment: android.app.Fragment, permissions: Array<String>, listener: OnPermissionResultListener?){
            PermissionFragment2.injectIfNeededIn(fragment)
            val permissionFragment = PermissionFragment2[fragment]
            permissionFragment.requestPermissions(permissions, listener)
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


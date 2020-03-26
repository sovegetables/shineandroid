package com.sovegetables.permission

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Permissions {

    companion object{

        @JvmStatic
        fun request(activity: AppCompatActivity, permission: String, listener: OnPermissionResultListener?){
            request(activity, arrayOf(permission), listener)
        }

        @JvmStatic
        fun request(activity: AppCompatActivity, permissions: Array<String>, listener: OnPermissionResultListener?){
            PermissionFragment.injectIfNeededIn(activity)
            val permissionFragment = PermissionFragment[activity]
            permissionFragment.requestPermissions(permissions, listener)
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

    }
}

fun AppCompatActivity.request(permissions: Array<String>, listener: OnPermissionResultListener?){
    Permissions.request(this, permissions, listener)
}

fun AppCompatActivity.request(permission: String, listener: OnPermissionResultListener?){
    Permissions.request(this, permission, listener)
}

fun Fragment.request(permissions: Array<String>, listener: OnPermissionResultListener?){
    Permissions.request(this, permissions, listener)
}

fun Fragment.request(permission: String, listener: OnPermissionResultListener?){
    Permissions.request(this, permission, listener)
}


package com.sovegetables.permission

import android.app.Activity
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Build
import android.util.SparseArray

class PermissionFragment2: Fragment() {

    companion object{

        private const val FRAGMENT_TAG =
            "com.sovegtables.permission_fragment2_tag"

        fun injectIfNeededIn(activity: Activity) {
            val manager = activity.fragmentManager
            if (manager.findFragmentByTag(FRAGMENT_TAG) == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    manager.beginTransaction().add(PermissionFragment2(), FRAGMENT_TAG)
                        .commitNowAllowingStateLoss()
                }else{
                    manager.beginTransaction().add(PermissionFragment2(), FRAGMENT_TAG)
                        .commitAllowingStateLoss()
                }
            }
        }

        fun injectIfNeededIn(fragment: Fragment){
            val manager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                fragment.childFragmentManager
            } else {
                fragment.fragmentManager
            }
            if(manager.findFragmentByTag(FRAGMENT_TAG) == null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    manager.beginTransaction().add(PermissionFragment2(), FRAGMENT_TAG)
                        .commitNowAllowingStateLoss()
                }else{
                    manager.beginTransaction().add(PermissionFragment2(), FRAGMENT_TAG)
                        .commitAllowingStateLoss()
                }
            }
        }

        internal operator fun get(fragment: Fragment): PermissionFragment2 {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                fragment.childFragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment2
            } else {
                fragment.fragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment2
            }
        }

        internal operator fun get(activity: Activity): PermissionFragment2 {
            return activity.fragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment2
        }
    }

    private val requestMap = SparseArray<PermissionRequest2>()
    private var requestCodeRecord = 0

    fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
        val request = PermissionRequest2(this, requestCodeRecord, permissions, listener)
        requestMap.put(requestCodeRecord, request)
        requestCodeRecord ++
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(request.permissions, request.requestCode)
        }else{
            val r = requestMap.get(request.requestCode)
            val grantResults = IntArray(permissions.size) {PackageManager.PERMISSION_GRANTED}
            r?.onHandlerPermissionsResult(permissions, grantResults)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val request = requestMap.get(requestCode)
        request?.onHandlerPermissionsResult(permissions, grantResults)
    }
}
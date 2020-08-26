package com.sovegetables.permission

import android.app.Activity
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.util.SparseArray

internal class PermissionCompatFragment: Fragment() {

    companion object{

        private const val FRAGMENT_TAG = "com.sovegtables.permission_fragment2_tag"
        private var IS_DEBUG = false

        fun injectIfNeededIn(activity: Activity): PermissionCompatFragment {
            val manager = activity.fragmentManager
            var pFragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionCompatFragment?
            if (pFragment == null) {
                val permissionCompatFragment = PermissionCompatFragment()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .commitNowAllowingStateLoss()
                }else{
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .commitAllowingStateLoss()
                }
                pFragment = permissionCompatFragment
            }
            return pFragment
        }

        fun injectIfNeededIn(fragment: Fragment) : PermissionCompatFragment{
            val manager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                fragment.childFragmentManager
            } else {
                fragment.fragmentManager
            }
            var pFragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionCompatFragment?
            if(pFragment == null){
                val permissionCompatFragment = PermissionCompatFragment()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .commitNowAllowingStateLoss()
                }else{
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .commitAllowingStateLoss()
                }
                pFragment = permissionCompatFragment
            }
            return pFragment
        }
    }

    private val requestMap = SparseArray<PermissionCompatRequest>()

    fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
        val requestCode = Util.createRequestCode()
        if(IS_DEBUG){
            Log.d("requestPermissions", "requestCode:$requestCode")
        }
        val request = PermissionCompatRequest(this, requestCode, permissions, listener)
        requestMap.put(requestCode, request)
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
        grantResults: IntArray) {
        val request = requestMap.get(requestCode)
        request?.onHandlerPermissionsResult(permissions, grantResults)
    }
}
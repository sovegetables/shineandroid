package com.sovegetables.permission

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.core.util.isEmpty
import androidx.core.util.isNotEmpty

internal class PermissionCompatFragment: Fragment() {

    companion object{

        private const val FRAGMENT_TAG = "com.sovegtables.permission_fragment2_tag"
        private var IS_DEBUG = false

        fun requestPermissions(activity: Activity, permissions: Array<String>, listener: OnPermissionResultListener?) {
            val manager = activity.fragmentManager
            val pFragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionCompatFragment?
            if (pFragment == null) {
                val permissionCompatFragment = PermissionCompatFragment()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG).runOnCommit{
                        permissionCompatFragment.requestPermissions(permissions, listener)
                    }.commitNowAllowingStateLoss()
                }else{
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .commitAllowingStateLoss()
                    permissionCompatFragment.requestPermissionsWithPending(permissions, listener)
                }
            }else{
                pFragment.requestPermissions(permissions, listener)
            }
        }

        fun requestPermissions(fragment: Fragment, permissions: Array<String>, listener: OnPermissionResultListener?){
            val manager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                fragment.childFragmentManager
            } else {
                fragment.fragmentManager
            }
            val pFragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionCompatFragment?
            if(pFragment == null){
                val permissionCompatFragment = PermissionCompatFragment()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .runOnCommit {
                            permissionCompatFragment.requestPermissions(permissions, listener)
                        }
                        .commitNowAllowingStateLoss()
                }else{
                    manager.beginTransaction().add(permissionCompatFragment, FRAGMENT_TAG)
                        .commitAllowingStateLoss()
                    permissionCompatFragment.requestPermissionsWithPending(permissions, listener)
                }
            }else{
                pFragment.requestPermissions(permissions, listener)
            }
        }
    }

    private val requestMap = SparseArray<PermissionCompatRequest>()
    private val requestMapWithPending = SparseArray<PermissionCompatRequest>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val size = requestMapWithPending.size()
        if(size > 0){
            for (item in 0 until size){
                val request = requestMapWithPending.get(requestMapWithPending.keyAt(item))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && request != null) {
                    requestPermissions(request.permissions, request.requestCode)
                }
            }
        }
    }

    private fun requestPermissionsWithPending(
        permissions: Array<String>,
        listener: OnPermissionResultListener?
    ) {
        val requestCode = Util.createRequestCode()
        if(IS_DEBUG){
            Log.d("requestPermissions", "requestCode:$requestCode")
        }
        val request = PermissionCompatRequest(this, requestCode, permissions, listener)
        requestMap.put(requestCode, request)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(host != null){
                requestPermissions(request.permissions, request.requestCode)
            }else{
                requestMapWithPending.put(requestCode, request)
            }
        }else{
            val r = requestMap.get(request.requestCode)
            val grantResults = IntArray(permissions.size) {PackageManager.PERMISSION_GRANTED}
            r?.onHandlerPermissionsResult(permissions, grantResults)
            requestMap.remove(requestCode)
        }
    }

    private fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
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
            requestMap.remove(requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        val request = requestMap.get(requestCode)
        if(request != null){
            request.onHandlerPermissionsResult(permissions, grantResults)
            requestMap.remove(requestCode)
            requestMapWithPending.remove(requestCode)
        }
    }
}
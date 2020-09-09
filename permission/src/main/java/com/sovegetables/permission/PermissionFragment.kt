package com.sovegetables.permission

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

internal class PermissionFragment : Fragment(){

    companion object{

        private const val FRAGMENT_TAG = "com.sovegtables.permission_fragment_tag"
        private var IS_DEBUG = false

        fun requestPermissions(activity: FragmentActivity, permissions: Array<String>, listener: OnPermissionResultListener?){
            val manager = activity.supportFragmentManager
            val fragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment?
            if (fragment == null) {
                val permissionFragment = PermissionFragment()
                manager.beginTransaction()
                    .add(permissionFragment, FRAGMENT_TAG)
                    .runOnCommit {
                        permissionFragment.requestPermissions(permissions, listener)
                    }
                    .commitNowAllowingStateLoss()
            }else{
                fragment.requestPermissions(permissions, listener)
            }
        }

        fun requestPermissions(fragment: Fragment, permissions: Array<String>, listener: OnPermissionResultListener?){
            val manager = fragment.childFragmentManager
            val pFragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment?
            if(pFragment == null){
                val permissionFragment = PermissionFragment()
                manager.beginTransaction().add(permissionFragment, FRAGMENT_TAG)
                    .runOnCommit {
                        permissionFragment.requestPermissions(permissions, listener)
                    }
                    .commitNowAllowingStateLoss()
            }else{
                pFragment.requestPermissions(permissions, listener)
            }
        }

    }

    private val requestMap = SparseArray<PermissionRequest>()
    private val requestMapWithPending = SparseArray<PermissionRequest>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val size = requestMapWithPending.size()
        if(size > 0){
            for (item in 0 until size){
                val request = requestMapWithPending.get(requestMapWithPending.keyAt(item))
                if(request != null){
                    requestPermissions(request.permissions, request.requestCode)
                }
            }
        }
    }

    private fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
        val requestCode = Util.createRequestCode()
        if(IS_DEBUG){
            Log.d("requestPermissions", "requestCode:$requestCode")
        }
        val request = PermissionRequest(this, requestCode, permissions, listener)
        requestMap.put(requestCode, request)
        if(host != null){
            requestPermissions(request.permissions, request.requestCode)
        }else{
            requestMapWithPending.put(requestCode, request)
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
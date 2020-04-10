package com.sovegetables.permission

import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

internal class PermissionFragment : Fragment(){

    companion object{

        private const val FRAGMENT_TAG =
            "com.sovegtables.permission_fragment_tag"

        fun injectIfNeededIn(activity: AppCompatActivity) {
            val manager = activity.supportFragmentManager
            if (manager.findFragmentByTag(FRAGMENT_TAG) == null) {
                manager.beginTransaction().add(PermissionFragment(), FRAGMENT_TAG)
                    .commitNowAllowingStateLoss()
            }
        }

        fun injectIfNeededIn(fragment: Fragment){
            val manager = fragment.childFragmentManager
            if(manager.findFragmentByTag(FRAGMENT_TAG) == null){
                manager.beginTransaction().add(PermissionFragment(), FRAGMENT_TAG)
                    .commitNowAllowingStateLoss()
            }
        }

        internal operator fun get(fragment: Fragment): PermissionFragment {
            return fragment.childFragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment
        }

        internal operator fun get(activity: AppCompatActivity): PermissionFragment {
            return activity.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment
        }
    }

    private val requestMap = SparseArray<PermissionRequest>()
    private var requestCodeRecord = 0

    fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
        val request = PermissionRequest(this, requestCodeRecord, permissions, listener)
        requestMap.put(requestCodeRecord, request)
        requestCodeRecord ++
        requestPermissions(request.permissions, request.requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val request = requestMap.get(requestCode)
        request?.onHandlerPermissionsResult(permissions, grantResults)
    }

    class PermissionRequest(var fragment: Fragment, var requestCode: Int, var permissions: Array<out String>, var listener: OnPermissionResultListener?){

        fun onHandlerPermissionsResult(permissions: Array<out String>?, grantResults: IntArray?) {
            if(permissions != null && grantResults != null && permissions.isNotEmpty() && grantResults.isNotEmpty()){
                val size = permissions.size
                val results = arrayListOf<PermissionResult>()
                for (i in 0 until size){
                    results.add(
                        PermissionResult(
                            permissions[i],
                            grantResults[i] == PackageManager.PERMISSION_GRANTED,
                            fragment.shouldShowRequestPermissionRationale(permissions[i])
                        )
                    )
                }

                var granted = false
                for (r in results){
                    granted = r.granted
                    if(!granted){
                        break
                    }
                }
                if(granted){
                    listener?.allGranted(results)
                }else{
                    val grantedPermissions = arrayListOf<PermissionResult>()
                    val deniedPermissions = arrayListOf<PermissionResult>()
                    for (r in results){
                        if(r.granted){
                            grantedPermissions.add(r)
                        }else{
                            deniedPermissions.add(r)
                        }
                    }
                    listener?.denied(grantedPermissions, deniedPermissions)
                }

            }
        }

    }
}
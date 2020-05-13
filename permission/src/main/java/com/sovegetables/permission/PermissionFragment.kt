package com.sovegetables.permission

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

internal class PermissionFragment : Fragment(){

    companion object{

        private const val FRAGMENT_TAG =
            "com.sovegtables.permission_fragment_tag"

        fun injectIfNeededIn(activity: FragmentActivity) {
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

        internal operator fun get(activity: FragmentActivity): PermissionFragment {
            return activity.supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment
        }
    }

    private val requestMap = SparseArray<PermissionRequest>()
    private var requestCodeRecord = 0

    fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
        val request = PermissionRequest(
            this,
            requestCodeRecord,
            permissions,
            listener
        )
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

}
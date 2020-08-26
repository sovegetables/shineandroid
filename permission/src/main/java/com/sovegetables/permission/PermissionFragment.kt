package com.sovegetables.permission

import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

internal class PermissionFragment : Fragment(){

    companion object{

        private const val FRAGMENT_TAG = "com.sovegtables.permission_fragment_tag"
        private var IS_DEBUG = true

        fun injectIfNeededIn(activity: FragmentActivity) : PermissionFragment{
            val manager = activity.supportFragmentManager
            var fragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment?
            if (fragment == null) {
                val permissionFragment = PermissionFragment()
                manager.beginTransaction().add(permissionFragment, FRAGMENT_TAG)
                    .commitNowAllowingStateLoss()
                fragment = permissionFragment;
            }
            return fragment
        }

        fun injectIfNeededIn(fragment: Fragment) : PermissionFragment{
            val manager = fragment.childFragmentManager
            var pFragment = manager.findFragmentByTag(FRAGMENT_TAG) as PermissionFragment?
            if(pFragment == null){
                val permissionFragment = PermissionFragment()
                manager.beginTransaction().add(permissionFragment, FRAGMENT_TAG)
                    .commitNowAllowingStateLoss()
                pFragment = permissionFragment
            }
            return pFragment
        }

    }

    private val requestMap = SparseArray<PermissionRequest>()

    fun requestPermissions(permissions:Array<out String>, listener: OnPermissionResultListener?) {
        val requestCode = Util.createRequestCode()
        if(IS_DEBUG){
            Log.d("requestPermissions", "requestCode:$requestCode")
        }
        val request = PermissionRequest(this, requestCode, permissions, listener)
        requestMap.put(requestCode, request)
        requestPermissions(request.permissions, request.requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        val request = requestMap.get(requestCode)
        request?.onHandlerPermissionsResult(permissions, grantResults)
    }

}
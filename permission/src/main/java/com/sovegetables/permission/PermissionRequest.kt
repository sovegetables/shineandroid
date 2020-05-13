package com.sovegetables.permission

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

internal class PermissionRequest(var fragment: Fragment, var requestCode: Int, var permissions: Array<out String>, var listener: OnPermissionResultListener?){

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
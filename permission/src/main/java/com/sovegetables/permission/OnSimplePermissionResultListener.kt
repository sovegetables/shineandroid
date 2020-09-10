package com.sovegetables.permission

abstract class OnSimplePermissionResultListener: OnPermissionResultListener {

    override fun denied(
        grantedPermissions: ArrayList<PermissionResult>,
        deniedPermissions: ArrayList<PermissionResult>
    ) {
    }
}
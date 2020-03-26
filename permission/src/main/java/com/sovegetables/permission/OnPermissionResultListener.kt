package com.sovegetables.permission

interface OnPermissionResultListener{
    fun allGranted(grantedPermissions: ArrayList<PermissionResult>)
    fun denied(grantedPermissions: ArrayList<PermissionResult>,
               deniedPermissions: ArrayList<PermissionResult>)
}
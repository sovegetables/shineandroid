package com.sovegetables.permission

data class PermissionResult(var permission: String, var granted: Boolean, var shouldShowPermissionRationale: Boolean)
package com.sovegetables.permission

import android.app.Activity

interface DeniedHandler {
    fun onHandler(activity: Activity, results: ArrayList<PermissionResult>)
}
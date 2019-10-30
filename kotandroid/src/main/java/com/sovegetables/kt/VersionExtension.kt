package com.sovegetables.kt

import android.os.Build

fun afterApi26(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
}

fun afterApi25(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
}

fun afterApi24(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}

fun afterApi23(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun afterApi21(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

fun afterApi20(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH
}

fun afterApi19(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
}

fun afterApi18(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
}

fun afterApi17(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
}

fun afterApi16(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
}
package com.sovegetables.viewpageadapter

interface IViewPageTitle {

    fun getPageTitle() : CharSequence?{
        return ""
    }

    fun getPageWidth(): Float{
        return 1.0f
    }
}
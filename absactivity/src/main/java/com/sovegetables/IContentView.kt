package com.sovegetables

import android.os.Bundle
import android.view.View
import com.sovegetables.topnavbar.ITopBarAction

interface IContentView {
    fun onCreate(savedInstanceState: Bundle?)
    fun onCreateContentView(view: View): View
    fun onCreateTopBarAction(): ITopBarAction
    fun addViewBelowTopBar(view: View)
    fun addViewBelowTopBar(layoutRes: Int)
    fun getEmptyController(): IEmptyController
    fun getLoadingController(): ILoadingController
    fun getLoadingDialogController(): ILoadingDialogController
}
package com.sovegetables.listactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ListViewModel(private var page: Page? = null): ViewModel() {

    private val internalSuccessLiveData = MutableLiveData<List<AbListItem>>()
    private val internalErrorLiveData = MutableLiveData<String>()

    companion object{

        private var sPage: Page? = null

        fun setDefaultPage(page: Page?){
            sPage = page
        }

    }

    fun getLiveData() : LiveData<List<AbListItem>>{
        return internalSuccessLiveData
    }

    fun getErrorLiveData(): LiveData<String>{
        return internalErrorLiveData
    }

    private val currentPage: Page

    init {
        if(page == null){
            page = sPage
        }
        if(page == null){
            page = Page(0, 10, -1, -1)
        }
        currentPage = page!!
    }

    data class Page(var initPage: Int, var pageSize: Int, var lastPage: Int, var total: Int){
        internal var currentPage: Int = initPage
    }

    fun loadFirst(){
        currentPage.currentPage = currentPage.initPage
        loadData(currentPage.currentPage, currentPage.pageSize, currentPage)
    }

    fun loadMore(){
        currentPage.currentPage += 1
        if(checkIfEnd(currentPage)){
            onLoadEnd(currentPage)
        }else{
            loadData(currentPage.currentPage, currentPage.pageSize, currentPage)
        }
    }

    protected abstract fun onLoadEnd(currentPage: Page)

    protected abstract fun checkIfEnd(currentPage: Page): Boolean

    protected abstract fun loadData(currentPage: Int, pageSize: Int, page: Page)
}
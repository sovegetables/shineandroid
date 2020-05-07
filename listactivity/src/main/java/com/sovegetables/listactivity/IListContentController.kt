package com.sovegetables.listactivity

import android.os.Bundle
import android.view.View
import com.sovegetables.adapter.AbsDelegationAdapter

interface IListContentController<T> {
    fun layoutRes(): Int
    fun onViewCreated(view: View?, savedInstanceState: Bundle?)
    fun setAdapter(adapter: AbsDelegationAdapter<List<*>>)
    fun setData(data: T?)
    fun setOnLoadMoreActionListener(l: OnLoadMoreActionListener?)
    fun onFinishedRefreshing()
    fun onFinishedLoadMore()
}
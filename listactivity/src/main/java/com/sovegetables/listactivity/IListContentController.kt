package com.sovegetables.listactivity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface IListContentController<T> {
    fun layoutRes(): Int
    fun onViewCreated(view: View?, savedInstanceState: Bundle?)
    fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>)
    fun setData(data: T)
}
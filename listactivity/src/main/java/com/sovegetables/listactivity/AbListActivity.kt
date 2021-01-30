package com.sovegetables.listactivity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sovegetables.BaseActivity
import com.sovegetables.adapter.AbsDelegationAdapter

abstract class AbListActivity<V: ListViewModel> : BaseActivity() {

    private lateinit var viewModel: V

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listContentController = getListContentController()
        val layoutRes = listContentController.layoutRes()
        val view = try {
            layoutInflater.inflate(layoutRes, findViewById(android.R.id.content), false)
        } catch (e: Exception) {
            layoutInflater.inflate(layoutRes, null)
        }
        setContentView(view)
        listContentController.onViewCreated(view, savedInstanceState)
        listContentController.setAdapter(getListAdapter())
        viewModel = ViewModelProviders.of(this).get(getViewModelClass())
        viewModel.getLiveData().observe(this, Observer<List<AbListItem>> { t -> listContentController.setData(t) })
        listContentController.setOnLoadMoreActionListener(object : OnLoadMoreActionListener{
            override fun loadMore() {
                viewModel.loadMore()
            }

            override fun onRefresh() {
                viewModel.loadFirst()
            }
        })
        viewModel.loadFirst()
    }

    protected abstract fun getViewModelClass(): Class<V>

    abstract fun getListAdapter(): AbsDelegationAdapter<List<*>>

    open fun getListContentController(): IListContentController<List<AbListItem>>{
        return Default()
    }
}

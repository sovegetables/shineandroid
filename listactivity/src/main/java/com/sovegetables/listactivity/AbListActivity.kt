package com.sovegetables.listactivity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sovegetables.BaseActivity
import com.sovegetables.adapter.AbsDelegationAdapter

abstract class AbListActivity<V: ListViewModel> : BaseActivity() {

    private lateinit var viewModel: V
    private var listContentController: IListContentController<List<AbListItem>>? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listContentController = getListContentController()
        this.listContentController = listContentController
        val layoutRes = listContentController.layoutRes()
        val view = layoutInflater.inflate(layoutRes, null)
        setContentView(view)
        listContentController.onViewCreated(view, savedInstanceState)
        listContentController.setAdapter(getListAdapter())
        viewModel = ViewModelProviders.of(this).get(getViewModelClass())
        viewModel.getLiveData().observe(this, Observer<List<AbListItem>> { t ->
            hideLoading()
            this.listContentController?.onFinishedLoadMore()
            this.listContentController?.onFinishedRefreshing()
            listContentController.setData(t)
        })
        listContentController.setOnLoadMoreActionListener(object : OnLoadMoreActionListener{
            override fun loadMore() {
                viewModel.loadMore()
            }

            override fun onRefresh() {
                viewModel.loadFirst()
            }
        })
        showLoading()
        viewModel.loadFirst()
        viewModel.getErrorLiveData().observe(this, Observer {
            hideLoading()
            this.listContentController?.onFinishedLoadMore()
            this.listContentController?.onFinishedRefreshing()
            onErrorPage(it)
        })
    }

    open fun onErrorPage(it: ListViewModel.PageError?) {}

    protected abstract fun getViewModelClass(): Class<V>

    abstract fun getListAdapter(): AbsDelegationAdapter<List<*>>

    open fun pageBuilder() : PageBuilder {
        return PageBuilder()
            .enableLoadMore(false)
            .enableAutoLoadMore(false)
            .enableRefresh(false)
    }

    open fun getListContentController(): IListContentController<List<AbListItem>>{
        val builder = pageBuilder()
        return Default(builder)
    }
}

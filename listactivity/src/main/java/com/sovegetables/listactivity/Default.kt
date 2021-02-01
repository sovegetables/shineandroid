package com.sovegetables.listactivity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.sovegetables.adapter.AbsDelegationAdapter

open class Default(var pageBuilder: PageBuilder): IListContentController<List<AbListItem>> {

    private lateinit var mShineRecyclerView: RecyclerView
    private lateinit var mShineSmartRefreshLayout: SmartRefreshLayout
    private var mAdapter: AbsDelegationAdapter<List<*>>? = null
    private var mOnLoadMoreActionListener: OnLoadMoreActionListener? = null

    override fun layoutRes(): Int {
        return R.layout.shine_activity_list
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mShineRecyclerView = view!!.findViewById(R.id.shine_recycler_view)
        mShineSmartRefreshLayout = view.findViewById(R.id.shine_smart_refresh_layout)
        val header = ClassicsHeader(view.context)
        header.setFinishDuration(0)
        val footer = ClassicsFooter(view.context)
        footer.setFinishDuration(0)
        mShineSmartRefreshLayout.setRefreshHeader(header)
        mShineSmartRefreshLayout.setRefreshFooter(footer)
        mShineSmartRefreshLayout.setEnableRefresh(pageBuilder.enableRefresh)
        mShineSmartRefreshLayout.setEnableLoadMore(pageBuilder.enableLoadMore)
        mShineSmartRefreshLayout.setEnableAutoLoadMore(pageBuilder.enableAutoLoadMore)
        mShineSmartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true)
        mShineSmartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true)
        mShineSmartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mOnLoadMoreActionListener?.loadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mOnLoadMoreActionListener?.onRefresh()
            }

        })
    }

    override fun setAdapter(adapter: AbsDelegationAdapter<List<*>>) {
        mAdapter = adapter
        mShineRecyclerView.adapter = adapter
    }

    override fun setData(data: List<AbListItem>?) {
        mAdapter?.items = data
    }

    override fun setOnLoadMoreActionListener(l: OnLoadMoreActionListener?){
        mOnLoadMoreActionListener = l
    }

    override fun onFinishedLoadMore() {
        mShineSmartRefreshLayout.finishLoadMore()
    }

    override fun onFinishedRefreshing() {
        mShineSmartRefreshLayout.finishRefresh()
    }
}
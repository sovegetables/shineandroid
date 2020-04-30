package com.sovegetables.listactivity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

open class Default: IListContentController<List<AbListItem>> {

    private lateinit var mShineRecyclerView: RecyclerView
    private lateinit var mShineSmartRefreshLayout: SmartRefreshLayout

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
        mShineSmartRefreshLayout.setEnableRefresh(true)
        mShineSmartRefreshLayout.setEnableAutoLoadMore(true)
        mShineSmartRefreshLayout.setEnableFooterFollowWhenNoMoreData(false)
        mShineSmartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onLoadMore(refreshLayout: RefreshLayout) {

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {

            }

        })
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        mShineRecyclerView.adapter = adapter
    }

    override fun setData(data: List<AbListItem>) {
        TODO("Not yet implemented")
    }
}
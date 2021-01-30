package com.sovegetables.sample

import android.os.Bundle
import com.sovegetables.adapter.AbsDelegationAdapter
import com.sovegetables.adapter.AbsListAdapter
import com.sovegetables.adapter.CommonViewHolder
import com.sovegetables.listactivity.AbListActivity
import com.sovegetables.listactivity.AbListItem
import com.sovegetables.listactivity.ListViewModel

class ListSampleActivity : AbListActivity<ListSampleActivity.BViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    class BViewModel: ListViewModel(){
        override fun onLoadEnd(currentPage: Page) {
        }

        override fun checkIfEnd(currentPage: Page): Boolean {
            return true
        }

        override fun loadData(currentPage: Int, pageSize: Int, page: Page) {
            val items = arrayListOf<AbListItem>()
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            items.add(object : ViewItem{})
            successLiveData().postValue(items)
        }

    }

    override fun getViewModelClass(): Class<BViewModel> {
        return BViewModel::class.java
    }

    interface ViewItem : AbListItem{

    }

    class ViewItemAbsListAdapter : AbsListAdapter<ViewItem>() {
        override fun onBindView(holder: CommonViewHolder?, t: ViewItem?, position: Int) {
        }

        override fun getLayoutRes(): Int {
            return R.layout.item_list
        }

    }

    override fun getListAdapter(): AbsDelegationAdapter<List<*>> {
        return ViewItemAbsListAdapter() as AbsDelegationAdapter<List<*>>
    }
}
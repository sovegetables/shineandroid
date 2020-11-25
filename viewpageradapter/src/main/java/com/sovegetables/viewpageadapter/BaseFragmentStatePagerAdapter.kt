package com.sovegetables.viewpageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class BaseFragmentStatePagerAdapter<T: Fragment>(fm: FragmentManager, behavior: Int= BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentStatePagerAdapter(fm, behavior) {

    private var fragments : List<T>? = null

    open fun setData(fragments: List<T>?){
        this.fragments = fragments
        notifyDataSetChanged()
    }

    open fun getData(): List<T>?{
        return fragments
    }

    override fun getItem(position: Int): T {
        return fragments?.get(position)!!
    }

    override fun getCount(): Int {
        return fragments?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val item = getItem(position)
        if(item is IViewPageTitle){
            return item.getPageTitle()
        }
        return super.getPageTitle(position)
    }

    override fun getPageWidth(position: Int): Float {
        val item = getItem(position)
        if(item is IViewPageTitle){
            return item.getPageWidth()
        }
        return super.getPageWidth(position)
    }
}
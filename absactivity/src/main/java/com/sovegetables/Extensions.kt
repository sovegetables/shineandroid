package com.sovegetables

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import com.sovegetables.topnavbar.TopBar
import com.sovegetables.topnavbar.TopBarItem

object Common{

    fun defaultLeftTopBarItem(context: Context, @DrawableRes icon: Int, listener: View.OnClickListener): TopBarItem {
        return TopBarItem.Builder()
            .listener(listener)
            .icon(icon)
            .build(context, 0)
    }

}

fun BaseActivity.defaultLeftTopBarItem(@DrawableRes icon: Int): TopBarItem {
    return Common.defaultLeftTopBarItem(this, icon,
        View.OnClickListener { this.onBackPressed() })
}

fun BaseActivity.titleTopBar(title: CharSequence?): TopBar {
    val leftItem = defaultLeftTopBarItem(BaseActivity.leftTopItemIconRes)
    return TopBar.Builder()
        .left(leftItem)
        .title(title)
        .build(this)
}

fun BaseActivity.titleBuilder(title: CharSequence?): TopBar.Builder {
    val leftItem = defaultLeftTopBarItem(BaseActivity.leftTopItemIconRes)
    return TopBar.Builder()
        .left(leftItem)
        .title(title)
}

fun BaseFragment.defaultLeftTopBarItem(@DrawableRes icon: Int): TopBarItem {
    return Common.defaultLeftTopBarItem(this.activity!!, icon,
        View.OnClickListener { this.activity!!.onBackPressed() })
}

fun BaseFragment.titleTopBar(title: CharSequence?): TopBar {
    val leftItem = defaultLeftTopBarItem(BaseActivity.leftTopItemIconRes)
    return TopBar.Builder()
        .left(leftItem)
        .title(title)
        .build(this.activity!!)
}

fun BaseFragment.titleBuilder(title: CharSequence?): TopBar.Builder {
    val leftItem = defaultLeftTopBarItem(BaseActivity.leftTopItemIconRes)
    return TopBar.Builder()
        .left(leftItem)
        .title(title)
}
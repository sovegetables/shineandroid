package com.sovegetables.sample

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.sovegetables.BaseActivity
import com.sovegetables.kt.toast
import com.sovegetables.titleBuilder
import com.sovegetables.topnavbar.TopBar
import com.sovegetables.topnavbar.TopBarItem

class BaseActivitySampleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_sample)

        Handler().postDelayed({
            topBarAction.findRightItemUpdaterById(4)
                .visibility(View.VISIBLE)
                .update()
        }, 4000)
    }

    override fun getTopBar(): TopBar {
        val right3 = TopBarItem.Builder()
            .icon(R.drawable.ic_baseline_share_24)
            .visibility(TopBarItem.Visibility.GONE)
            .listener {
                toast("点击右边的分享Icon")
            }
            .build(this, 4)
        return titleBuilder("BaseActivity Demo").right(right3).build(this)
    }
}

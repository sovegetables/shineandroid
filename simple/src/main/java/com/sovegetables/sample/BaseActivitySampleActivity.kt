package com.sovegetables.sample

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bumptech.glide.Glide
import com.sovegetables.BaseActivity
import com.sovegetables.kt.toast
import com.sovegetables.titleBuilder
import com.sovegetables.topnavbar.TopBar
import com.sovegetables.topnavbar.TopBarItem
import kotlinx.android.synthetic.main.activity_base_sample.*

class BaseActivitySampleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_sample)

        Handler().postDelayed({
            topBarAction.findRightItemUpdaterById(4)
                .visibility(View.VISIBLE)
                .update()
        }, 4000)

        btn_show_dialog_loading.setOnClickListener {
            showLoadingDialog(canceled = true)
        }

        btn_show_loading.setOnClickListener {
            showLoading()

            Handler().postDelayed({
                hideLoading()
            }, 2000)
        }

        Glide.with(iv).load(R.drawable.loading).into(iv)
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

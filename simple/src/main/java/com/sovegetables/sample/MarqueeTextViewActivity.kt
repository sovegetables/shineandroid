package com.sovegetables.sample

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.sovegetables.BaseActivity
import com.sovegetables.titleBuilder
import com.sovegetables.topnavbar.TopBar
import com.sovegetables.topnavbar.TopBarItem
import kotlinx.android.synthetic.main.activity_marquee_text_view.*

class MarqueeTextViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marquee_text_view)

        tv_marquee.setText("好哈佛爱活动复合大师粉红大是返回的萨芬")
        tv_marquee_2.setText("发发大水发的说法大沙发的是非得失")

//        Handler().postDelayed({
//            tv_marquee.visibility = View.GONE
//        } , 8000)
    }

    override fun getTopBar(): TopBar? {
        val left = TopBarItem.Builder()
            .icon(R.drawable.ic_delegate_arrow_back).text("MarqueeTextView")
            .listener {
                onBackPressed()
            }
            .textColorRes(android.R.color.black)
            .build(this, 0)
        val builder = titleBuilder()
        builder.left(left)
        return builder.build(this)
    }
}

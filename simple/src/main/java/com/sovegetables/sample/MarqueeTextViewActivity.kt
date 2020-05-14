package com.sovegetables.sample

import android.os.Bundle
import com.sovegetables.BaseActivity
import com.sovegetables.titleTopBar
import com.sovegetables.topnavbar.TopBar
import kotlinx.android.synthetic.main.activity_marquee_text_view.*

class MarqueeTextViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marquee_text_view)

        tv_marquee.setText("好哈佛爱活动复合大师粉红大是返回的萨芬")
    }

    override fun getTopBar(): TopBar? {
        return titleTopBar(" MarqueeTextView")
    }
}

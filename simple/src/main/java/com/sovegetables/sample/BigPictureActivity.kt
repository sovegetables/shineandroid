package com.sovegetables.sample

import android.os.Bundle
import com.bumptech.glide.Glide
import com.sovegetables.BaseActivity
import kotlinx.android.synthetic.main.activity_big_picture.*

class BigPictureActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_picture)

        val w = resources.displayMetrics.widthPixels
        // 750 2699
        val h = (2699 * w / 750f + 0.5f).toInt()

        var layoutParams = iv_simple.layoutParams
        layoutParams.width = w
        layoutParams.height = h

        Glide.with(iv_simple)
            .load(R.mipmap.login_bg)
            .into(iv_simple)

        layoutParams = iv_simple_b.layoutParams
        layoutParams.width = w
        layoutParams.height = h

        Glide.with(iv_simple_b)
            .load(R.mipmap.login_bg)
            .into(iv_simple_b)

//        nsc_big_pic
    }
}
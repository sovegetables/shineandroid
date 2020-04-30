package com.sovegetables.sample

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.sovegetables.BaseActivity
import com.sovegetables.kt.toast
import com.sovegetables.textcrumbs.TextCrumbs
import com.sovegetables.titleTopBar
import com.sovegetables.topnavbar.TopBar
import kotlinx.android.synthetic.main.activity_textcrumbs.*

class TextCrumbsActivity : BaseActivity() {

    class TextLabel(var labelStr: String, var labelId: Int): TextCrumbs.Label{
        override fun getLabel(): String {
            return labelStr
        }

        override fun getId(): Int {
            return labelId
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textcrumbs)

        val texts = ArrayList<TextCrumbs.Label>()
        texts.add(TextLabel("Java", 1))
        texts.add(TextLabel("Kotlin", 2))
        texts.add(TextLabel("Python", 3))
        texts.add(TextLabel("Javascript", 4))
        texts.add(TextLabel("C++", 5))
        texts.add(TextLabel("Ruby", 6))
        texts.add(TextLabel("PHP", 7))
        texts.add(TextLabel("Dart", 8))
        crumbs.addLabel(texts)
        crumbs.setOnClickFilterOptionListener {
            toast("${it.label}: ${it.id}")
        }
    }

    override fun getTopBar(): TopBar? {
        return titleTopBar("TextCrumb")
    }
}

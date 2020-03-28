package com.sovegetables.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sovegetables.BaseActivity
import com.sovegetables.SystemBarConfig
import com.sovegetables.adapter.AbsListAdapter
import com.sovegetables.adapter.CommonViewHolder
import com.sovegetables.titleBuilder
import com.sovegetables.topnavbar.TopBar
import com.sovegetables.topnavbar.TopBarItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = object : AbsListAdapter<MainItem>() {
            override fun onBindView(holder: CommonViewHolder, t: MainItem, position: Int) {
                val tvTitle = holder.findViewById<TextView>(R.id.tv_title)
                tvTitle.text = t.title
            }

            override fun getLayoutRes(): Int {
                return R.layout.item_main_item
            }

        }
        adapter.setOnItemClickListener { view, t, position ->
            val intent = Intent(this, t.clazz)
            startActivity(intent)
        }
        val list = arrayListOf<MainItem>()
        list.add(MainItem("标题栏Sample", TopBarSimpleActivity::class.java))
        list.add(MainItem("BaseActivity Sample", BaseActivitySampleActivity::class.java))
        list.add(MainItem("权限Sample", PermissionSampleMainActivity::class.java))
        list.add(MainItem("TextCrumb Sample", TextCrumbsActivity::class.java))
        adapter.items = list
        rv_sample.adapter = adapter

        Handler().postDelayed({
            systemBarHelper.setStatusBarColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
        }, 3000)
    }

    data class MainItem(var title: String, var clazz: Class<out Activity>)

    override fun getTopBar(): TopBar {
        return titleBuilder("Sample").left(TopBarItem.EMPTY).build(this)
    }

    override fun createSystemBarConfig(): SystemBarConfig {
        return SystemBarConfig(statusBarColor = ContextCompat.getColor(this, android.R.color.holo_blue_light))
    }
}

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
import com.sovegetables.topnavbar.TopBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
        list.add(MainItem("BaseFragmentSample", FragmentSampleActivity::class.java))
        list.add(MainItem("FragmentPageSample", FragmentPageSampleActivity::class.java))
        list.add(MainItem("MarqueeTextViewSample", MarqueeTextViewActivity::class.java))
        list.add(MainItem("大图片Sample", BigPictureActivity::class.java))
        list.add(MainItem("BottomBarActivity", BottomTabActionActivity::class.java))
        list.add(MainItem("ListActivity", ListSampleActivity::class.java))
        adapter.items = list
        rv_sample.adapter = adapter

        Handler().postDelayed({
            systemBarHelper.setStatusBarColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
        }, 3000)
    }

    data class MainItem(var title: String, var clazz: Class<out Activity>)

    override fun getTopBar(): TopBar {
        return TopBar.NO_ACTION_BAR
    }

    override fun createSystemBarConfig(): SystemBarConfig {
        return SystemBarConfig.Builder()
            .setStatusBarColorRes(android.R.color.holo_blue_light)
            .build()
    }
}

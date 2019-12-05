package com.sovegetables.sample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.sovegetables.kt.toast
import com.sovegetables.logger.ILog
import com.sovegetables.logger.Logger
import com.sovegetables.topnavbar.TopBar
import com.sovegetables.topnavbar.TopBarItem
import kotlinx.android.synthetic.main.activity_main.*

class TopBarSimpleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "TopBarSimpleActivity"

        init {
            val logImpl = Logger.Delegates()
            logImpl.addDelegate(LogImpl())
            logImpl.addDelegate(Logger.ANDROID)
            Logger.setDelegate(logImpl)
        }
    }

    class LogImpl: ILog(){
        override fun println(priority: LEVEL, tag: String?, msg: String?) {
            when(priority){
                LEVEL.VERBOSE -> {
                    Log.d("LogImpl", "VERBOSE: ")
                }
                LEVEL.DEBUG -> {
                    Log.d("LogImpl", "DEBUG: ")
                }
                LEVEL.INFO -> {
                    Log.d("LogImpl", "INFO: ")
                }
                LEVEL.WARN -> {
                    Log.d("LogImpl", "WARN: ")
                }
                LEVEL.ERROR -> {
                    Log.d("LogImpl", "ERROR: ")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.d(TAG, "onCreate-----")

        val left = TopBarItem.Builder()
            .icon(R.drawable.ic_baseline_menu_24)
            .listener {
                toast("点击左边的Icon")
            }
            .build(this, 1)

        val right1 = TopBarItem.Builder()
            .icon(R.drawable.ic_baseline_add_circle_24)
            .listener {
                toast("点击右边的添加Icon")
            }
            .build(this, 2)

        val right2 = TopBarItem.Builder()
            .icon(R.drawable.ic_baseline_sms_24)
            .listener {
                toast("点击右边的消息Icon")
            }
            .visibility(TopBarItem.Visibility.GONE)
            .build(this, 3)

        val right3 = TopBarItem.Builder()
            .icon(R.drawable.ic_baseline_share_24)
            .listener {
                toast("点击右边的分享Icon")
            }
            .build(this, 4)

        val rights = arrayListOf<TopBarItem>()
        rights.add(right1)
        rights.add(right2)
        rights.add(right3)

        val topBar = TopBar.Builder()
            .title("标题1")
            .left(left)
            .right(right3)
            .titleColor(Color.BLACK)
            .topBarColorRes(R.color.colorPrimary)
            .build(this)
        action_bar.setUpTopBar(topBar)

        val left2 = TopBarItem.Builder()
            .icon(R.drawable.ic_baseline_menu_24)
            .text("标题2")
            .textColor(Color.WHITE)
            .listener {
                toast("点击左边的Icon")
            }
            .build(this, 1)
        val topBar2 = TopBar.Builder()
            .left(left2)
            .rights(rights)
            .topBarColorRes(R.color.colorPrimary)
            .build(this)
        action_bar_2.setUpTopBar(topBar2)

        Handler().postDelayed({
            action_bar.topBarUpdater
                .title("更新标题1")
                .titleColorRes(R.color.colorAccent)
                .topBarColor(Color.BLACK)
                .update();

            action_bar_2.findRightItemUpdaterById(3)
                .visibility(View.VISIBLE)
                .update()

//            action_bar_2.findRightItemUpdaterById(4)
//                .visibility(View.GONE)
//                .update()

        }, 2000)

    }
}

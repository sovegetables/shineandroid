package com.sovegetables

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.qmuiteam.qmui.widget.QMUIEmptyView
import com.sovegetables.topnavbar.ActionBarView
import com.sovegetables.topnavbar.ITopBarAction

class DefaultIContentView: IContentView {

    private var actionBarView: ActionBarView? = null
    private var flContent: FrameLayout? = null
    private var context: Context? = null
    private var qmuiEmptyView: QMUIEmptyView? = null
    private var actionBarContainer: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    override fun onCreateContentView(view: View): View {
        context = view.context
        val rootView = LayoutInflater.from(view.context).inflate(R.layout.shine_layout_activity_base_delegate, null)
        actionBarContainer = rootView.findViewById(R.id.ll_delegate_action_bar_container)
        actionBarView = rootView.findViewById(R.id.delegate_action_bar)
        flContent = rootView.findViewById(R.id.fl_base_delegate_content)
        qmuiEmptyView = rootView.findViewById(R.id.qmui_empty_view)
        flContent!!.addView(view)
        return rootView
    }

    override fun onCreateTopBarAction(): ITopBarAction {
        return actionBarView!!
    }

    override fun addViewBelowTopBar(view: View) {
        actionBarContainer?.addView(view)
    }

    override fun addViewBelowTopBar(layoutRes: Int) {
        actionBarContainer?.addView(LayoutInflater.from(context).inflate(layoutRes, actionBarContainer, false))
    }

    override fun getEmptyController(): IEmptyController {
        return IEmptyController.NOT
    }

    override fun getLoadingController(): ILoadingController {
        return object : ILoadingController{
            override fun showLoading() {
                flContent?.visibility = View.GONE
                qmuiEmptyView?.visibility = View.VISIBLE
                qmuiEmptyView?.setLoadingShowing(true)
            }

            override fun hideLoading() {
                flContent?.visibility = View.VISIBLE
                qmuiEmptyView?.visibility = View.GONE
                qmuiEmptyView?.setLoadingShowing(false)
            }

            override fun isLoadingShow(): Boolean {
                return qmuiEmptyView?.isShowing?: false && qmuiEmptyView?.isLoading()?:false
            }

        }
    }

    override fun getLoadingDialogController(): ILoadingDialogController {
       return QUMILoadingController(context!!)
    }

}
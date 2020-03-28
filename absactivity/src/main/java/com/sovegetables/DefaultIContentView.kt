package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.sovegetables.topnavbar.ActionBarView
import com.sovegetables.topnavbar.ITopBarAction

class DefaultIContentView: IContentView {

    private var actionBarView: ActionBarView? = null
    private var flContent: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    override fun onCreateContentView(view: View?): View {
        val rootView = LayoutInflater.from(view!!.context).inflate(R.layout.shine_layout_activity_base_delegate, null)
        actionBarView = rootView.findViewById(R.id.delegate_action_bar)
        flContent = rootView.findViewById(R.id.fl_base_delegate_content)
        flContent!!.addView(view)
        return rootView
    }

    override fun onCreateTopBarAction(): ITopBarAction {
        return actionBarView!!
    }

}
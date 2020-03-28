package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.sovegetables.topnavbar.ITopBarAction
import com.sovegetables.topnavbar.TopBar

abstract class BaseFragment : Fragment(){

    companion object{

        private var sDefaultContentViewDelegate: IContentView? = null

        fun setsDefaultContentViewDelegate(defaultContentViewDelegate: IContentView?) {
            sDefaultContentViewDelegate = defaultContentViewDelegate
        }
    }

    private lateinit var topBarAction: ITopBarAction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val contentView = onBaseCreateView(inflater, container, savedInstanceState)
        sDefaultContentViewDelegate = getContentViewDelegate()
        if (sDefaultContentViewDelegate == null) {
            sDefaultContentViewDelegate = DefaultIContentView()
        }
        val realContentView = sDefaultContentViewDelegate!!.onCreateContentView(contentView)
        topBarAction = sDefaultContentViewDelegate!!.onCreateTopBarAction()
        topBarAction.setUpTopBar(getTopBar())
        return realContentView
    }

    fun getTopBarAction(): ITopBarAction{
        return topBarAction
    }

    open fun getTopBar(): TopBar? {
        return TopBar.NO_ACTION_BAR
    }

    open fun getContentViewDelegate(): IContentView? {
        return null
    }

    open fun onBaseCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId(), container, false)
    }

    @LayoutRes open fun layoutId(): Int{
        return 0
    }

}
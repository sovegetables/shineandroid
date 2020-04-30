package com.sovegetables

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.sovegetables.topnavbar.ITopBarAction
import com.sovegetables.topnavbar.TopBar

abstract class BaseFragment : Fragment(), IEmptyController, ILoadingDialogController, ILoadingController{

    companion object{

        private var sDefaultContentViewDelegate: IContentView? = null

        fun setsDefaultContentViewDelegate(defaultContentViewDelegate: IContentView?) {
            sDefaultContentViewDelegate = defaultContentViewDelegate
        }
    }

    protected lateinit var topBarAction: ITopBarAction
    protected lateinit var loadingDialogController: ILoadingDialogController
    protected lateinit var loadingController: ILoadingController
    protected lateinit var emptyController: IEmptyController

    private var realContentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return onBaseCreateView(inflater, container, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        replaceContentView(view)
    }

    private fun replaceContentView(view: View) {
        sDefaultContentViewDelegate = getContentViewDelegate()
        if (sDefaultContentViewDelegate == null) {
            sDefaultContentViewDelegate = DefaultIContentView()
        }
        val parent = view.parent
        if (parent is ViewGroup) {
            parent.removeAllViews()
            realContentView = sDefaultContentViewDelegate!!.onCreateContentView(view)
            topBarAction = sDefaultContentViewDelegate!!.onCreateTopBarAction()
            topBarAction.setUpTopBar(getTopBar())
            loadingDialogController = sDefaultContentViewDelegate!!.getLoadingDialogController()
            emptyController = sDefaultContentViewDelegate!!.getEmptyController()
            loadingController = sDefaultContentViewDelegate!!.getLoadingController()
            parent.addView(realContentView)
        }
    }

    override fun getView(): View? {
        return realContentView
    }

    open fun getTopBar(): TopBar? {
        return TopBar.NO_ACTION_BAR
    }

    open fun getContentViewDelegate(): IContentView? {
        return null
    }

    open fun addViewBelowTopBar(view: View) {
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)){
            sDefaultContentViewDelegate!!.addViewBelowTopBar(view)
        }
    }

    open fun addViewBelowTopBar(@LayoutRes layoutRes: Int) {
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)){
            sDefaultContentViewDelegate!!.addViewBelowTopBar(layoutRes)
        }
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

    override fun showEmpty(msg: String?, icon: Int, model: IEmptyController.Model?) {
        emptyController.showEmpty(msg, icon, model)
    }

    override fun hideEmpty() {
        emptyController.hideEmpty()
    }

    override fun showLoadingDialog(
        msg: String?,
        icon: Int,
        canceled: Boolean,
        model: ILoadingDialogController.Model?
    ) {
        loadingDialogController.showLoadingDialog(msg, icon, canceled, model)
    }

    override fun hideLoadingDialog() {
        loadingDialogController.hideLoadingDialog()
    }

    override fun isLoadingDialogShow(): Boolean {
        return loadingDialogController.isLoadingDialogShow()
    }

    override fun showLoading() {
        loadingController.showLoading()
    }

    override fun hideLoading() {
        loadingController.hideLoading()
    }

    override fun isLoadingShow(): Boolean {
        return loadingController.isLoadingShow()
    }
}
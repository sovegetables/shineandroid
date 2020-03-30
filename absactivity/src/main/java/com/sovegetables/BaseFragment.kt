package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
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
        loadingDialogController = sDefaultContentViewDelegate!!.getLoadingDialogController()
        emptyController = sDefaultContentViewDelegate!!.getEmptyController()
        loadingController = sDefaultContentViewDelegate!!.getLoadingController()
        return realContentView
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
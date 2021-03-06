package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.sovegetables.topnavbar.ITopBarAction
import com.sovegetables.topnavbar.TopBar

abstract class BaseFragment : Fragment(), IEmptyController, ILoadingDialogController, ILoadingController{

    protected var topBarAction: ITopBarAction? = null
    protected var loadingDialogController: ILoadingDialogController? = null
    protected var loadingController: ILoadingController? = null
    protected var emptyController: IEmptyController? = null
    private var contentViewDelegate: IContentView? = null
    private var realContentView: View? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        if(realContentView == null){
            val view = onBaseCreateView(inflater, container, savedInstanceState)
            if(view != null){
                contentViewDelegate = getContentViewDelegate()
                if (contentViewDelegate == null) {
                    contentViewDelegate = DefaultIContentView()
                }
                realContentView = contentViewDelegate!!.onCreateContentView(view)
                topBarAction = contentViewDelegate!!.onCreateTopBarAction()
                topBarAction!!.setUpTopBar(getTopBar())
                loadingDialogController = contentViewDelegate!!.getLoadingDialogController()
                emptyController = contentViewDelegate!!.getEmptyController()
                loadingController = contentViewDelegate!!.getLoadingController()
            }
        }
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
            contentViewDelegate?.addViewBelowTopBar(view)
        }
    }

    open fun addViewBelowTopBar(@LayoutRes layoutRes: Int) {
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)){
            contentViewDelegate?.addViewBelowTopBar(layoutRes)
        }
    }

    open fun onBaseCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    @LayoutRes open fun layoutId(): Int{
        return 0
    }

    override fun showEmpty(msg: String?, icon: Int, model: IEmptyController.Model?) {
        emptyController?.showEmpty(msg, icon, model)
    }

    override fun hideEmpty() {
        emptyController?.hideEmpty()
    }

    override fun showLoadingDialog(
        msg: String?,
        icon: Int,
        canceled: Boolean,
        model: ILoadingDialogController.Model?
    ) {
        loadingDialogController?.showLoadingDialog(msg, icon, canceled, model)
    }

    override fun hideLoadingDialog() {
        loadingDialogController?.hideLoadingDialog()
    }

    override fun isLoadingDialogShow(): Boolean {
        return loadingDialogController?.isLoadingDialogShow()?:false
    }

    override fun showLoading() {
        loadingController?.showLoading()
    }

    override fun hideLoading() {
        loadingController?.hideLoading()
    }

    override fun isLoadingShow(): Boolean {
        return loadingController?.isLoadingShow()?:false
    }
}
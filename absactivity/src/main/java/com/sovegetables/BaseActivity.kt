package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import cn.albert.autosystembar.SystemBarHelper
import com.sovegetables.topnavbar.ITopBarAction
import com.sovegetables.topnavbar.TopBar

abstract class BaseActivity : AppCompatActivity(), IEmptyController, ILoadingDialogController, ILoadingController{

    companion object{
        private var sDefaultContentViewDelegate: IContentView? = null
        @DrawableRes var leftTopItemIconRes = R.drawable.ic_delegate_arrow_back

        fun setDefaultContentViewDelegate(defaultContentViewDelegate: IContentView?) {
            sDefaultContentViewDelegate = defaultContentViewDelegate
        }

        fun setLeftTopIcon(@DrawableRes res: Int) {
            leftTopItemIconRes = res
        }

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    protected lateinit var topBarAction: ITopBarAction
    protected lateinit var systemBarHelper: SystemBarHelper
    protected lateinit var loadingDialogController: ILoadingDialogController
    protected lateinit var loadingController: ILoadingController
    protected lateinit var emptyController: IEmptyController

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        configSystemBar(createSystemBarConfig())
        sDefaultContentViewDelegate = getContentViewDelegate<IContentView>()
        if (sDefaultContentViewDelegate == null) {
            sDefaultContentViewDelegate = FragmentContentView()
        }
        sDefaultContentViewDelegate!!.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(loadingDialogController.isLoadingDialogShow()){
            loadingDialogController.hideLoadingDialog()
        }
    }

    private fun configSystemBar(systemBarConfig: SystemBarConfig) {
        val builder = SystemBarHelper.Builder()
        builder.enableImmersedNavigationBar(systemBarConfig.navigationBarImmersed)
        builder.enableImmersedStatusBar(systemBarConfig.statusBarImmersed)
        if (systemBarConfig.navigationBarColor != SystemBarConfig.NOT_SET) {
            builder.navigationBarColor(systemBarConfig.navigationBarColor)
        }
        if (systemBarConfig.statusBarColor != SystemBarConfig.NOT_SET) {
            builder.statusBarColor(systemBarConfig.statusBarColor)
        }
        builder.statusBarFontStyle(if (systemBarConfig.statusBarFontStyle === SystemBarConfig.SystemBarFontStyle.LIGHT) SystemBarHelper.STATUS_BAR_LIGHT_FONT_STYLE else SystemBarHelper.STATUS_BAR_DARK_FONT_STYLE)
        builder.navigationBarStyle(if (systemBarConfig.navigationBarStyle === SystemBarConfig.SystemBarFontStyle.LIGHT) SystemBarHelper.NAVIGATION_BAR_LIGHT_ICON_STYLE else SystemBarHelper.NAVIGATION_BAR_DARK_ICON_STYLE)
        systemBarHelper = builder.into(this)
    }

    open fun createSystemBarConfig(): SystemBarConfig {
        return SystemBarConfig()
    }

    final override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        setContentView(view)
    }

    final override fun setContentView(view: View?) {
        val realContentView = sDefaultContentViewDelegate!!.onCreateContentView(view!!)
        super.setContentView(realContentView)
        topBarAction = sDefaultContentViewDelegate!!.onCreateTopBarAction()
        topBarAction.setUpTopBar(getTopBar())
        loadingDialogController = sDefaultContentViewDelegate!!.getLoadingDialogController()
        emptyController = sDefaultContentViewDelegate!!.getEmptyController()
        loadingController = sDefaultContentViewDelegate!!.getLoadingController()
    }

    open fun getTopBar(): TopBar? {
        return TopBar.NO_ACTION_BAR
    }

    open fun <T : IContentView?> getContentViewDelegate(): T? {
        return null
    }

    override fun setContentView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ) {
        throw UnsupportedOperationException("Not support!")
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
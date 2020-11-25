package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import cn.albert.autosystembar.SystemBarHelper
import com.sovegetables.topnavbar.ITopBarAction
import com.sovegetables.topnavbar.TopBar

abstract class BaseActivity : AppCompatActivity(), IEmptyController, ILoadingDialogController, ILoadingController{

    companion object{
        @DrawableRes var leftTopItemIconRes = R.drawable.ic_delegate_arrow_back

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
    private lateinit var contentViewDelegate: IContentView

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        configSystemBar(createSystemBarConfig())
        var childContentViewDelegate = getContentViewDelegate<IContentView>()
        if (childContentViewDelegate == null) {
            childContentViewDelegate = DefaultIContentView()
        }
        contentViewDelegate = childContentViewDelegate
        contentViewDelegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(loadingDialogController.isLoadingDialogShow()){
            loadingDialogController.hideLoadingDialog()
        }
    }

    private fun configSystemBar(systemBarConfig: SystemBarConfig?) {
        if(systemBarConfig != null){
            val builder = SystemBarHelper.Builder()
            builder.enableImmersedNavigationBar(systemBarConfig.navigationBarImmersed)
            builder.enableImmersedStatusBar(systemBarConfig.statusBarImmersed)
            if(systemBarConfig.isStatusBarColorSet){
                builder.statusBarColor(systemBarConfig.statusBarColor)
            }
            if(systemBarConfig.isNavigationBarColorSet){
                builder.navigationBarColor(systemBarConfig.navigationBarColor)
            }
            if(systemBarConfig.statusBarColorRes > 0){
                builder.statusBarColorRes(systemBarConfig.statusBarColorRes)
            }
            if(systemBarConfig.navigationBarColorRes > 0){
                builder.navigationBarColorRes(systemBarConfig.navigationBarColorRes)
            }
            if(systemBarConfig.statusBarFontStyle != SystemBarConfig.SystemBarFontStyle.NOT_SET){
                builder.statusBarFontStyle(if (systemBarConfig.statusBarFontStyle === SystemBarConfig.SystemBarFontStyle.LIGHT) SystemBarHelper.STATUS_BAR_LIGHT_FONT_STYLE else SystemBarHelper.STATUS_BAR_DARK_FONT_STYLE)
            }
            if(systemBarConfig.navigationBarStyle != SystemBarConfig.SystemBarFontStyle.NOT_SET){
                builder.navigationBarStyle(if (systemBarConfig.navigationBarStyle === SystemBarConfig.SystemBarFontStyle.LIGHT) SystemBarHelper.NAVIGATION_BAR_LIGHT_ICON_STYLE else SystemBarHelper.NAVIGATION_BAR_DARK_ICON_STYLE)
            }
            systemBarHelper = builder.into(this)
        }
    }

    open fun createSystemBarConfig(): SystemBarConfig? {
        return null
    }

    @CallSuper
    override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        setContentView(view)
    }

    override fun setContentView(view: View?) {
        val realContentView = contentViewDelegate.onCreateContentView(view!!)
        super.setContentView(realContentView)
        topBarAction = contentViewDelegate.onCreateTopBarAction()
        topBarAction.setUpTopBar(getTopBar())
        loadingDialogController = contentViewDelegate.getLoadingDialogController()
        emptyController = contentViewDelegate.getEmptyController()
        loadingController = contentViewDelegate.getLoadingController()
    }

    open fun getTopBar(): TopBar? {
        return TopBar.NO_ACTION_BAR
    }

    open fun <T : IContentView?> getContentViewDelegate(): T? {
        return null
    }

    open fun addViewBelowTopBar(view: View) {
        contentViewDelegate.addViewBelowTopBar(view)
    }

    open fun addViewBelowTopBar(@LayoutRes layoutRes: Int) {
        contentViewDelegate.addViewBelowTopBar(layoutRes)
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
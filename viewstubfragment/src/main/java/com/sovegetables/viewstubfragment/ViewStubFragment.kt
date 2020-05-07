package com.sovegetables.viewstubfragment

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.sovegetables.BaseFragment

abstract class ViewStubFragment: BaseFragment(){

    private var hasInflated = false
    private var mViewStub: ViewStub? = null
    private var mSavedInstanceState: Bundle? = null

    final override fun layoutId(): Int {
        return R.layout.fragment_shine_stub
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!hasInflated){
            mViewStub = view.findViewById(R.id.shine_fragment_view_stub)
            mViewStub!!.layoutResource = layoutIdRes()
            showLoading()
        }
        mSavedInstanceState = savedInstanceState
        if(!enableLazyLoading()){
            loadRealLayout()
        }
    }

    private fun loadRealLayout() {
        if (!hasInflated && mViewStub != null) {
            hasInflated = true
            val inflatedView = mViewStub!!.inflate()
            onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState)
            hideLoading()
        }
    }

    @CallSuper
    final override fun onResume() {
        super.onResume()
        onPreLazyResume()
        loadRealLayout()
        onLazyResume()
    }

    protected open fun onPreLazyResume() {
    }

    protected open fun onLazyResume() {
    }

    protected open fun enableLazyLoading(): Boolean {
        return true
    }

    protected abstract fun onCreateViewAfterViewStubInflated(inflatedView: View, savedInstanceState: Bundle?)

    /**
     * The layout ID associated with this ViewStub
     * @see ViewStub.setLayoutResource
     * @return
     */
    @LayoutRes
    protected abstract fun layoutIdRes(): Int

}
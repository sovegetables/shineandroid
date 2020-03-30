package com.sovegetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sovegetables.topnavbar.ITopBarAction

class FragmentContentView: IContentView {

    private var savedInstanceState: Bundle? = null
    private var interFragment: InterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
    }

    class InterFragment: BaseFragment(){
        private var interView : View? = null

        internal fun topBarAction(): ITopBarAction{
            return topBarAction
        }

        internal fun loadingDialogController(): ILoadingDialogController{
            return loadingDialogController
        }
        internal fun loadingController(): ILoadingController{
            return loadingController
        }

        internal fun emptyController(): IEmptyController{
            return emptyController
        }

        override fun onBaseCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return interView!!
        }

        fun setInterView(view: View) {
            interView = view
        }
    }

    override fun onCreateContentView(view: View): View {
        val layoutInflater = LayoutInflater.from(view.context)
        val rootView = layoutInflater.inflate(R.layout.shine_layout_fragment_root, null)
        //不使用fragmentManager, 因为commit的fragment不是马上走onCreateView方法的
        interFragment = InterFragment()
        interFragment!!.setInterView(view)
        val createView = interFragment!!.onCreateView(layoutInflater, rootView as ViewGroup, savedInstanceState)
        rootView.addView(createView)
        return rootView
    }

    override fun onCreateTopBarAction(): ITopBarAction {
        return interFragment?.topBarAction()?: ITopBarAction.NO_TOP_BAR_ACTION
    }

    override fun getEmptyController(): IEmptyController {
        return interFragment?.emptyController()?: IEmptyController.NOT
    }

    override fun getLoadingController(): ILoadingController {
        return interFragment?.loadingController()?: ILoadingController.NOT
    }

    override fun getLoadingDialogController(): ILoadingDialogController {
        return interFragment?.loadingDialogController()?: ILoadingDialogController.NOT
    }
}
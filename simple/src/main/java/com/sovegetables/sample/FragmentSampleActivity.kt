package com.sovegetables.sample

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.LayoutInflater.Factory2
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.LayoutInflaterFactory
import androidx.fragment.app.Fragment
import com.sovegetables.BaseFragment
import com.sovegetables.titleTopBar
import com.sovegetables.topnavbar.TopBar
import kotlinx.android.synthetic.main.activity_base_sample.*
import java.lang.reflect.Field

class FragmentSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_layout_inflator)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_l, InnerFragment())
            .commitNowAllowingStateLoss()
    }

    class InnerFragment: BaseFragment(){

        private var mContainerId: Int? = null

        internal inner class Factory2Wrapper(val layoutInflater: LayoutInflater, val factory2: Factory2?) : Factory2 {

            fun getFragmentContainer(): Int?{
                if(mContainerId != null){
                    return mContainerId
                }
                try {
                    val mContainerField = Fragment::class.java.getDeclaredField("mContainerId")
                    mContainerField.isAccessible = true
                    mContainerId = mContainerField.get(this@InnerFragment) as Int?
                } catch (e: Exception) {
                }
                return mContainerId
            }

            override fun onCreateView(
                name: String,
                context: Context,
                attrs: AttributeSet
            ): View? {
                return onCreateView(null, name, context, attrs)
            }

            override fun onCreateView(
                parent: View?, name: String, context: Context,
                attributeSet: AttributeSet
            ): View? {
                var onCreateView: View? = null
                if(factory2 != null){
                    onCreateView = factory2.onCreateView(parent, name, context, attributeSet)
                }
                if(onCreateView == null && layoutInflater.factory != null){
                    onCreateView = layoutInflater.factory.onCreateView(name, context, attributeSet)
                }
                if(onCreateView == null){
                    onCreateView = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        layoutInflater.onCreateView(context, parent, name, attributeSet)
                    } else {
                        layoutInflater.createView(name, "", attributeSet)
                    }
                }
//
//                if(onCreateView != null && parent!= null && parent.id == getFragmentContainer()){
//                    val rootView = if(parent is ViewGroup){
//                        LayoutInflater.from(context).inflate(R.layout.test_layout_activity_base_delegate, parent, false)
//                    }else{
//                        LayoutInflater.from(context).inflate(R.layout.test_layout_activity_base_delegate, null)
//                    }
//                    val flContent = rootView.findViewById<ViewGroup>(R.id.fl_base_delegate_content)
//                    getFragmentContainer()!!.removeAllViews()
//                    flContent.addView(onCreateView)
//                    getFragmentContainer()!!.addView(flContent)
//                    return flContent
//                }

                return onCreateView
            }

            override fun toString(): String {
                return javaClass.name
            }

        }

        companion object{

            private const val TAG = "TestLayoutInflaterActivity"

            private var sCheckedField = false
            private var sLayoutInflaterFactory2Field: Field? = null

            private fun forceSetFactory2(inflater: LayoutInflater, factory: Factory2) {
                if (!sCheckedField) {
                    try {
                        sLayoutInflaterFactory2Field =
                            LayoutInflater::class.java.getDeclaredField("mFactory2")
                        sLayoutInflaterFactory2Field!!.isAccessible = true
                    } catch (e: NoSuchFieldException) {
                        Log.e(TAG,
                            "forceSetFactory2 Could not find field 'mFactory2' on class "
                                    + LayoutInflater::class.java.name
                                    + "; inflation may have unexpected results.",
                            e
                        )
                    }
                    sCheckedField = true
                }
                if (sLayoutInflaterFactory2Field != null) {
                    try {
                        sLayoutInflaterFactory2Field!![inflater] = factory
                    } catch (e: IllegalAccessException) {
                        Log.e(TAG,
                            "forceSetFactory2 could not set the Factory2 on LayoutInflater "
                                    + inflater + "; inflation may have unexpected results.",
                            e
                        )
                    }
                }
            }
        }


        override fun onBaseCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val inflate = inflater.inflate(R.layout.fragment_test, container, false)
            return inflate
        }

        override fun getTopBar(): TopBar? {
            return titleTopBar("BaseFragment")
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            btn_show_dialog_loading.setOnClickListener {
                showLoadingDialog(canceled = true)
            }

            btn_show_loading.setOnClickListener {
                showLoading()

                Handler().postDelayed({
                    hideLoading()
                }, 2000)
            }
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
        }

        override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
            val inflater = super.onGetLayoutInflater(savedInstanceState)
            val factory2 = inflater.factory2
            if(factory2 == null){
                LayoutInflaterCompat.setFactory2(inflater, Factory2Wrapper(inflater, factory2))
            }else{
                forceSetFactory2(inflater, Factory2Wrapper(inflater, factory2))
            }
            return inflater
        }

    }
}

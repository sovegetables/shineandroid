package com.sovegetables.sample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sovegetables.BaseFragment
import com.sovegetables.viewpageadapter.BaseFragmentPagerAdapter
import com.sovegetables.viewpageadapter.BaseFragmentStatePagerAdapter
import com.sovegetables.viewpageadapter.IViewPageTitle
import com.sovegetables.viewstubfragment.ViewStubFragment
import kotlinx.android.synthetic.main.activity_base_sample.*
import kotlinx.android.synthetic.main.activity_base_sample.btn_show_dialog_loading
import kotlinx.android.synthetic.main.activity_base_sample.btn_show_loading
import kotlinx.android.synthetic.main.activity_fragment_page_sample.*
import kotlinx.android.synthetic.main.fragment_test.*

class FragmentPageSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_page_sample)

        val adapter = object : BaseFragmentPagerAdapter<InternalFragment>(supportFragmentManager) {}
//        val adapter = object : BaseFragmentStatePagerAdapter<InternalFragment>(supportFragmentManager) {}
        view_page.adapter = adapter

        val fragments = arrayListOf<InternalFragment>()
        for(i in 1..10){
            fragments.add(InternalFragment.getBy(i))
        }
        adapter.setData(fragments)
    }

    class InternalFragment: ViewStubFragment(), IViewPageTitle{

        companion object{

            private const val KEY_PAGE = "key.InternalFragment"

            fun getBy(page: Int) : InternalFragment{
                val fragment = InternalFragment()
                val bundle = Bundle()
                bundle.putInt(KEY_PAGE, page)
                fragment.arguments = bundle
                return fragment
            }

            private fun getPage(fragment: InternalFragment): Int{
                return fragment.arguments?.getInt(KEY_PAGE)?:0
            }

        }

        override fun onCreateViewAfterViewStubInflated(
            inflatedView: View,
            savedInstanceState: Bundle?
        ) {
            btn_show_dialog_loading.setOnClickListener {
                showLoadingDialog(canceled = true)
            }

            btn_show_loading.setOnClickListener {
                showLoading()

                Handler().postDelayed({
                    hideLoading()
                }, 2000)
            }

            tv_page.text = getPageTitle()
        }

        override fun layoutIdRes(): Int {
            return R.layout.fragment_test
        }

        override fun getPageTitle(): CharSequence? {
            return "Page ${getPage(this)}"
        }
    }


}

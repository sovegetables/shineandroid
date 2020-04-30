package com.sovegetables.listactivity

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sovegetables.BaseActivity
import java.lang.reflect.ParameterizedType

abstract class AbListActivity<out V: ListViewModel> : BaseActivity() {

    private lateinit var viewModel: V

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listContentController = getListContentController()
        val layoutRes = listContentController.layoutRes()
        val view = try {
            layoutInflater.inflate(layoutRes, findViewById(android.R.id.content), false)
        } catch (e: Exception) {
            layoutInflater.inflate(layoutRes, null)
        }
        setContentView(view)
        listContentController.onViewCreated(view, savedInstanceState)
        listContentController.setAdapter(getListAdapter())

        viewModel = ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory()).get(getVClass()!!)
        viewModel.getLiveData().observe(this, object : Observer<AbListItem>{
            override fun onChanged(t: AbListItem?) {

            }
        })
        viewModel.loadFirst()
    }

    @SuppressWarnings("unchecked")
    private fun getVClass(): Class<V>? {
        val clazz: Class<*> = this.javaClass
        val type = clazz.genericSuperclass
        if (type is ParameterizedType) {
            val typeArgs = type.actualTypeArguments
            return typeArgs[0] as Class<V>
        }
        return null
    }

    abstract fun getListAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder>

    open fun getListContentController(): IListContentController<List<AbListItem>>{
        return Default()
    }
}

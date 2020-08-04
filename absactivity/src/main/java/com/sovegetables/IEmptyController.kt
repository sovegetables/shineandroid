package com.sovegetables

import android.view.View
import androidx.annotation.DrawableRes

interface IEmptyController {

    companion object{
        val NOT = EMPTY()
    }

    data class Model(var title: String? = null, @DrawableRes var icon: Int = 0, var detailMsg: String? = null ,var btnText: String? = null, var listener: View.OnClickListener? = null)

    fun showEmpty(msg: String? = null, icon: Int = 0, model: Model? = null)
    fun hideEmpty()

    class EMPTY: IEmptyController{
        override fun showEmpty(msg: String?, icon: Int, model: Model?) {
        }

        override fun hideEmpty() {
        }
    }

}
package com.sovegetables

import androidx.annotation.DrawableRes

interface IEmptyController {

    companion object{
        val NOT = EMPTY()
    }

    data class Model(var msg: String? = null, @DrawableRes var icon: Int = 0)

    fun showEmpty(msg: String? = null, icon: Int = 0, model: Model? = null)
    fun hideEmpty()

    class EMPTY: IEmptyController{
        override fun showEmpty(msg: String?, icon: Int, model: Model?) {
        }

        override fun hideEmpty() {
        }
    }

}
package com.sovegetables

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

interface ILoadingDialogController {

    companion object{
        val NOT = EMPTY()
    }

    class EMPTY: ILoadingDialogController{
        override fun showLoadingDialog(
            msg: String?,
            icon: Int,
            canceled: Boolean,
            model: Model?
        ) {

        }

        override fun hideLoadingDialog() {

        }

        override fun isLoadingDialogShow(): Boolean {
            return false
        }
    }

    data class Model(var msg: String? = null, @DrawableRes var icon: Int = 0, @ColorInt var color: Int = Color.BLACK, var canceled: Boolean = true)

    fun showLoadingDialog(
        msg: String? = null,
        icon: Int = 0,
        canceled: Boolean = true,
        model: Model? = null
    )
    fun hideLoadingDialog()
    fun isLoadingDialogShow(): Boolean

}
package com.sovegetables

import androidx.annotation.ColorInt

data class SystemBarConfig constructor(@ColorInt var statusBarColor: Int = NOT_SET, var navigationBarColor: Int = NOT_SET,
                                       var statusBarImmersed: Boolean = false, var navigationBarImmersed: Boolean = false,
                                       var statusBarFontStyle: SystemBarFontStyle = SystemBarFontStyle.LIGHT,
                                       var navigationBarStyle: SystemBarFontStyle = SystemBarFontStyle.LIGHT){

    companion object {
        const val NOT_SET = -1
    }

    enum class SystemBarFontStyle{
        LIGHT, DARK
    }

}
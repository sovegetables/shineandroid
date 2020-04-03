package com.sovegetables

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

class SystemBarConfig private constructor() {

    @ColorInt internal var statusBarColor: Int = 0
    internal var isStatusBarColorSet = false
    @ColorRes internal var statusBarColorRes: Int = 0
    @ColorInt internal var navigationBarColor: Int = 0
    internal var isNavigationBarColorSet = false
    @ColorRes internal var navigationBarColorRes: Int = 0
    internal var statusBarImmersed: Boolean = false
    internal var navigationBarImmersed: Boolean = false
    internal var statusBarFontStyle = SystemBarFontStyle.NOT_SET
    internal var navigationBarStyle = SystemBarFontStyle.NOT_SET

    constructor(statusBarImmersed: Boolean) : this() {
        this.statusBarImmersed = statusBarImmersed
    }

    constructor(statusBarImmersed: Boolean, statusBarFontStyle: SystemBarFontStyle) : this() {
        this.statusBarImmersed = statusBarImmersed
        this.statusBarFontStyle = statusBarFontStyle
    }

    constructor(statusBarColor: Int, statusBarFontStyle: SystemBarFontStyle): this(){
        isStatusBarColorSet = true
        this.statusBarColor = statusBarColor
        this.statusBarFontStyle = statusBarFontStyle
    }

    class Builder{

        @ColorInt private var statusBarColor: Int = 0
        private var isStatusBarColorSet = false
        @ColorRes private var statusBarColorRes: Int = 0
        @ColorInt private var navigationBarColor: Int = 0
        private var isNavigationBarColorSet = false
        @ColorRes private var navigationBarColorRes: Int = 0
        private var statusBarImmersed: Boolean = false
        private var navigationBarImmersed: Boolean = false
        private var statusBarFontStyle = SystemBarFontStyle.NOT_SET
        private var navigationBarStyle = SystemBarFontStyle.NOT_SET

        fun setStatusBarColorRes(@ColorRes statusBarColorRes: Int) = apply {
            this.statusBarColorRes = statusBarColorRes
        }

        fun setStatusBarColor(@ColorInt statusBarColor: Int) = apply {
            isStatusBarColorSet = true
            this.statusBarColor = statusBarColor
        }

        fun setNavigationBarColorRes(@ColorInt navigationBarColorRes: Int) = apply {
            this.navigationBarColorRes = navigationBarColorRes
        }

        fun setNavigationBarColor(@ColorRes navigationBarColor: Int) = apply {
            isNavigationBarColorSet = true
            this.navigationBarColor = navigationBarColor
        }

        fun setStatusBarImmersed(statusBarImmersed: Boolean) = apply {
            this.statusBarImmersed = statusBarImmersed
        }

        fun setNavigationBarImmersed(navigationBarImmersed: Boolean) = apply {
            this.navigationBarImmersed = navigationBarImmersed
        }

        fun setStatusBarFontStyle(statusBarFontStyle: SystemBarFontStyle) = apply {
            this.statusBarFontStyle = statusBarFontStyle
        }

        fun setNavigationBarStyle(navigationBarStyle: SystemBarFontStyle) = apply {
            this.navigationBarStyle = navigationBarStyle
        }

        fun build(): SystemBarConfig{
            val config = SystemBarConfig()
            config.isNavigationBarColorSet = isNavigationBarColorSet
            config.isStatusBarColorSet = isStatusBarColorSet
            config.statusBarColor = statusBarColor
            config.statusBarColorRes = statusBarColorRes
            config.navigationBarColor = navigationBarColor
            config.navigationBarColorRes = navigationBarColorRes
            config.navigationBarImmersed = navigationBarImmersed
            config.statusBarImmersed = statusBarImmersed
            config.navigationBarStyle = navigationBarStyle
            config.statusBarFontStyle = statusBarFontStyle
            return config
        }
    }



    enum class SystemBarFontStyle{
        LIGHT, DARK, NOT_SET
    }

}
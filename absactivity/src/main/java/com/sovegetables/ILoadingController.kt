package com.sovegetables

interface ILoadingController {

    companion object{
        val NOT = EMPTY()
    }

    class EMPTY: ILoadingController{
        override fun showLoading() {
        }

        override fun hideLoading() {
        }

        override fun isLoadingShow(): Boolean {
            return false
        }
    }

    fun showLoading()
    fun hideLoading()
    fun isLoadingShow(): Boolean
}
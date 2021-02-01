package com.sovegetables.listactivity

class PageBuilder{
    internal var enableLoadMore = false
    internal var enableRefresh = false
    internal var enableAutoLoadMore = false

    fun enableLoadMore(enable: Boolean): PageBuilder {
        enableLoadMore = enable
        return this
    }

    fun enableAutoLoadMore(enable: Boolean): PageBuilder {
        enableAutoLoadMore = enable
        return this
    }

    fun enableRefresh(enable: Boolean) : PageBuilder {
        enableRefresh = enable
        return this
    }
}
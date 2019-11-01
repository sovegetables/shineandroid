package com.sovegetables.logger

import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

abstract class ILog {

    enum class LEVEL{
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    open fun v(tag: String?, msg: String?) {
        println(LEVEL.VERBOSE, tag, msg)
    }

    open fun v(tag: String?, msg: String, tr: Throwable?) {
        println(LEVEL.VERBOSE, tag, msg + '\n' + getStackTraceString(tr))
    }

    open fun d(tag: String?, msg: String?) {
        println(LEVEL.DEBUG, tag, msg)
    }

    open fun d(tag: String?, msg: String, tr: Throwable?) {
        println(LEVEL.DEBUG, tag, msg + '\n' + getStackTraceString(tr))
    }

    open fun i(tag: String?, msg: String?) {
        println(LEVEL.INFO, tag, msg)
    }

    open fun i(tag: String?, msg: String, tr: Throwable?) {
        println(LEVEL.INFO, tag, msg + '\n' + getStackTraceString(tr))
    }

    open fun w(tag: String?, msg: String?) {
        println(LEVEL.WARN, tag, msg)
    }

    open fun w(tag: String?, msg: String, tr: Throwable?) {
        println(LEVEL.WARN, tag, msg + '\n' + getStackTraceString(tr))
    }

    open fun w(tag: String?, tr: Throwable?) {
        println(LEVEL.WARN, tag, getStackTraceString(tr))
    }

    open fun e(tag: String?, msg: String?) {
        println(LEVEL.ERROR, tag, msg)
    }

    open fun e(tag: String?, msg: String, tr: Throwable?) {
        println(LEVEL.ERROR, tag, msg + '\n' + getStackTraceString(tr))
    }

    protected open fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    abstract fun println(priority: LEVEL, tag: String?, msg: String?)
}
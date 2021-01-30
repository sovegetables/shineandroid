package com.sovegetables.logger

import android.util.Log

class Logger private constructor() : ILog(){

    class Android : ILog(){
        override fun println(priority: LEVEL, tag: String?, msg: String?) {
            when(priority){
                LEVEL.VERBOSE -> Log.v(tag?:"null", msg?: "null")
                LEVEL.DEBUG -> Log.d(tag?:"null", msg?: "null")
                LEVEL.INFO -> Log.i(tag?:"null", msg?: "null")
                LEVEL.WARN -> Log.w(tag?:"null", msg?: "null")
                LEVEL.ERROR -> Log.e(tag?:"null", msg?: "null")
            }
        }
    }

    class Delegates : ILog(){
        private val delegates = arrayListOf<ILog>()
        fun addDelegate(log: ILog?){
            requireNotNull(log) { "ILog can't be null !!!" }
            delegates.add(log)
        }

        override fun println(priority: LEVEL, tag: String?, msg: String?) {
            delegates.forEach {
                it.println(priority, tag, msg)
            }
        }
    }

    companion object{

        private var sLogger : ILog? = null
        private var logger = Logger()
        val ANDROID = Android()

        fun setDelegate(logger: ILog?){
            sLogger = logger
        }

        fun v(tag: String?, msg: String?) {
            logger.v(tag, msg)
        }

        fun v(tag: String?, msg: String, tr: Throwable?) {
            logger.v(tag, msg, tr)
        }

        fun d(tag: String?, msg: String?) {
            logger.d(tag, msg)
        }

        fun d(tag: String?, msg: String, tr: Throwable?) {
            logger.d(tag, msg, tr)
        }

        fun i(tag: String?, msg: String?) {
            logger.i(tag, msg)
        }

        fun i(tag: String?, msg: String, tr: Throwable?) {
            logger.i(tag, msg, tr)
        }

        fun w(tag: String?, msg: String?) {
            logger.w(tag, msg)
        }

        fun w(tag: String?, msg: String, tr: Throwable?) {
            logger.w(tag, msg, tr)
        }

        fun w(tag: String?, tr: Throwable?) {
            logger.w(tag, tr)
        }

        fun e(tag: String?, msg: String?) {
            logger.e(tag, msg)
        }

        fun e(tag: String?, msg: String, tr: Throwable?) {
            logger.e(tag, msg, tr)
        }
    }

    override fun println(priority: LEVEL, tag: String?, msg: String?) {
        if(sLogger == null){
            ANDROID.println(priority, tag, msg)
        }else{
            sLogger!!.println(priority, tag, msg)
        }
    }
}
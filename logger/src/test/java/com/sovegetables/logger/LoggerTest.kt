package com.sovegetables.logger

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoggerTest {

    companion object{
        private const val TAG = "LoggerTest"
    }

    open class TestLog: ILog(){
        override fun println(priority: LEVEL, tag: String?, msg: String?) {
        }
    }

    @Test
    fun test() {
        val msg = "Test"
        val log = Mockito.mock(TestLog::class.java)
        Logger.setDelegate(log)
        Logger.v(TAG, msg)
        Mockito.verify(log).println(ILog.LEVEL.VERBOSE, TAG, msg)
        Logger.d(TAG, msg)
        Mockito.verify(log).println(ILog.LEVEL.DEBUG, TAG, msg)
        Logger.e(TAG, msg)
        Mockito.verify(log).println(ILog.LEVEL.ERROR, TAG, msg)
        Logger.i(TAG, msg)
        Mockito.verify(log).println(ILog.LEVEL.INFO, TAG, msg)
    }
}

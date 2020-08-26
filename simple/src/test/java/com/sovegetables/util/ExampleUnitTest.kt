package com.sovegetables.util

import org.junit.Test
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val executor: ExecutorService = Executors.newCachedThreadPool()
        executor.submit(Runnable {

        })
        executor.shutdown()
    }
}

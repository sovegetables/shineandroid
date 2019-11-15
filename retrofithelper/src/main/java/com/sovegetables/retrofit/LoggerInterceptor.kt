package com.sovegetables.retrofit

import com.sovegetables.logger.ILog
import com.sovegetables.logger.Logger
import okhttp3.*
import okio.Buffer
import java.io.IOException

class LoggerInterceptor: Interceptor{

    companion object{
        private val TAG = "LoggerInterceptor"
    }

    class InterceptorLogger: ILog() {

        override fun println(priority: LEVEL, tag: String?, msg: String?) {
            if(TAG == tag){

            }
        }

    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        printRequest(request)
        val response = chain.proceed(request)
        val body = response.body()
        val code = response.code()
        val headers = response.headers()
        val strBody = if (body == null) "" else body.string()
        printResponse(strBody, headers, code)
        val newBody = ResponseBody.create(body?.contentType(), strBody)
        return response.newBuilder().body(newBody).build()
    }

    private fun printResponse(body: String, headers: Headers, code: Int) {
        Logger.d(TAG, "response code:$code")
        Logger.d(TAG, "body:$body")
        Logger.d(TAG, "headers:$headers")
    }

    private fun printRequest(request: Request) {
        try {
            Logger.d(TAG, "url:" + request.url())
            Logger.d(TAG, "method:" + request.method())
            Logger.d(TAG, "headers:" + request.headers())
            val buffer = Buffer()
            val body = request.body()
            if (body != null) {
                body.writeTo(buffer)
                Logger.d(TAG, "body:" + buffer.readByteString().utf8())
            }
        } catch (e: Exception) {
            Logger.e(TAG, "printRequest", e)
        }

    }
}
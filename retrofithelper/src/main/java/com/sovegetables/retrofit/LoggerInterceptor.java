package com.sovegetables.retrofit;


import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.*;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {

    private static final String TAG = "LoggerInterceptor %s";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequest(request);
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        int code = response.code();
        Headers headers = response.headers();
        String strBody = body == null? "" : body.string();
        printResponse(strBody, headers, code);
        ResponseBody newBody = ResponseBody.create(body == null? null: body.contentType(), strBody);
        return response.newBuilder().body(newBody).build();
    }

    private void printResponse(String body, Headers headers, int code) {
        Log.d(TAG, "response code:" +  code);
        Log.d(TAG, "body:" +  body);
        Log.d(TAG, "headers:" +  headers);
    }

    private void printRequest(Request request) {
        Log.d(TAG, "url:" +  request.url());
        Log.d(TAG, "method:" +  request.method());
        Log.d(TAG, "headers:" +  request.headers());
        Log.d(TAG, "headers:" +  request.body().contentType().toString());
        Buffer buffer = new Buffer();
        try {
            request.body().writeTo(buffer);
        } catch (Exception ignored) {
        }
        Log.d(TAG, "body:" +  buffer.readByteString().utf8());
    }
}

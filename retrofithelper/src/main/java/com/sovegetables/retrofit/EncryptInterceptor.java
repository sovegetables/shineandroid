package com.sovegetables.retrofit;


import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by albert on 2018/8/8.
 *
 */

public class EncryptInterceptor implements Interceptor{

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedSink sink = Okio.buffer(Okio.sink(outputStream));
        RequestBody requestBody = request.body();
        requestBody.writeTo(sink);
        String requestStr = outputStream.toString();
        requestStr = aesEncode(requestStr); // 加密
        RequestBody newRequestBody = RequestBody.create(requestBody.contentType(), requestStr);
        Request newRequest = request.newBuilder()
                .post(newRequestBody)
                .build();
        Response response = chain.proceed(newRequest);
        ResponseBody body = response.body();
        String strBody = body == null? "" : body.string();
        String content = aesDecode(strBody);// 解密
        ResponseBody newBody = ResponseBody.create(body == null? null: body.contentType(), content);
        return response.newBuilder().body(newBody).build();
    }

    private String aesDecode(String strBody) {
        //加密
        return null;
    }

    private String aesEncode(String string){
        //解密
        return null;
    }
}

package com.sovegetables.retrofit;



import androidx.collection.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private final Retrofit sRetrofit;
    private static RetrofitUtil sRetrofitUtil;
    private final ArrayMap<Class, Object> mServiceCache = new ArrayMap<>();
    private final String baseUrl;

    private RetrofitUtil(String baseUrl, List<Interceptor> interceptors) {
        this.baseUrl = baseUrl;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoggerInterceptor());
        for (Interceptor i: interceptors){
            builder.addInterceptor(i);
        }
        OkHttpClient client = builder.build();
        sRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void init(String baseUrl, Interceptor interceptor){
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(interceptor);
        init(baseUrl, interceptors);
    }

    public static void init(){

    }

    public static void init(String baseUrl, List<Interceptor> interceptors){
        sRetrofitUtil = new RetrofitUtil(baseUrl, interceptors);
    }

    private static Retrofit commonRetrofit(){
        if (sRetrofitUtil == null) {
            throw new NullPointerException("Please init!!!");
        }
        return sRetrofitUtil.sRetrofit;
    }

    public static Retrofit createNewRetrofit(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoggerInterceptor());
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .baseUrl(sRetrofitUtil.baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service){
        Object o = sRetrofitUtil.mServiceCache.get(service);
        if(o != null){
            return ((T) o);
        }
        T t = commonRetrofit().create(service);
        sRetrofitUtil.mServiceCache.put(service, t);
        return t;
    }
}

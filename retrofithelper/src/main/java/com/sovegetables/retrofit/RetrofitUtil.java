package com.sovegetables.retrofit;


import androidx.collection.ArrayMap;

import retrofit2.Retrofit;

public class RetrofitUtil {

    private static Retrofit sRetrofit;
    private static RetrofitUtil sRetrofitUtil;
    private final ArrayMap<Class, Object> serviceCache = new ArrayMap<>();

    private RetrofitUtil() {
        //no instance
    }

    private static Retrofit commonRetrofit(){
        if (sRetrofitUtil == null) {
            throw new NullPointerException("Please invoke setSingletonRetrofit method to initialize!");
        }
        return sRetrofit;
    }

    public static Retrofit cloneRetrofit(String baseUrl){
        return sRetrofit
                .newBuilder()
                .baseUrl(baseUrl)
                .build();
    }

    public static void setSingletonRetrofit(Retrofit retrofit){
        sRetrofit = retrofit;
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service){
        Object o = sRetrofitUtil.serviceCache.get(service);
        if(o != null){
            return ((T) o);
        }
        T t = commonRetrofit().create(service);
        sRetrofitUtil.serviceCache.put(service, t);
        return t;
    }
}

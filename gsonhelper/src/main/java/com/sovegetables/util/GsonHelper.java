package com.sovegetables.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonHelper {

    private volatile Gson sGson;
    private static final GsonHelper sGsonHelper = new GsonHelper();

    private GsonHelper() {
        sGson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, new MistakeBooleanDeserializer())
                .registerTypeAdapter(boolean.class, new MistakeBooleanDeserializer())
                .registerTypeAdapter(Integer.class, new MistakeIntDeserializer())
                .registerTypeAdapter(int.class, new MistakeIntDeserializer())
                .registerTypeAdapter(float.class, new MistakeFloatDeserializer())
                .registerTypeAdapter(Float.class, new MistakeFloatDeserializer())
                .registerTypeAdapter(long.class, new MistakeLongDeserializer())
                .registerTypeAdapter(Long.class, new MistakeLongDeserializer())
                .registerTypeAdapter(double.class, new MistakeDoubleDeserializer())
                .registerTypeAdapter(Double.class, new MistakeDoubleDeserializer())
                .registerTypeAdapter(List.class, new MistakeListDeserializer())
                .addSerializationExclusionStrategy(new SkipExclusionStrategy())
                .addDeserializationExclusionStrategy(new SkipExclusionStrategy())
                .create();
    }

    public static Gson commonGson(){
        return sGsonHelper.sGson;
    }

    public static GsonBuilder cloneGsonBuilder(){
        return sGsonHelper.sGson.newBuilder();
    }

    public static String toJson(Object src){
        return sGsonHelper.sGson.toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return sGsonHelper.sGson.toJson(src, typeOfSrc);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException{
        return sGsonHelper.sGson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type type) throws JsonSyntaxException{
        return sGsonHelper.sGson.fromJson(json, type);
    }
}

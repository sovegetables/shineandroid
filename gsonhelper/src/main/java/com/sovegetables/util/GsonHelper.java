package com.sovegetables.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.List;

public class GsonHelper {

    private volatile Gson sGson;
    private static final GsonHelper sGsonHelper = new GsonHelper();

    private GsonHelper() {
        sGson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, new MistakeBooleanDeserializer())
                .registerTypeAdapter(boolean.class, new MistakeBooleanDeserializer())
                .registerTypeAdapter(Integer.class, new MistakeIntForNumberDeserializer())
                .registerTypeAdapter(int.class, new MistakeIntForNumberDeserializer())
                .registerTypeAdapter(float.class, new MistakeFloatForNumberDeserializer())
                .registerTypeAdapter(Float.class, new MistakeFloatForNumberDeserializer())
                .registerTypeAdapter(long.class, new MistakeLongForNumberDeserializer())
                .registerTypeAdapter(Long.class, new MistakeLongForNumberDeserializer())
                .registerTypeAdapter(double.class, new MistakeDoubleForNumberDeserializer())
                .registerTypeAdapter(Double.class, new MistakeDoubleForNumberDeserializer())
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

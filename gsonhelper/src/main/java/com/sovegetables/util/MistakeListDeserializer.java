package com.sovegetables.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MistakeListDeserializer implements JsonDeserializer<List> {

    @Override
    public List deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonArray()){
            Gson newGson = new Gson();
            return newGson.fromJson(json, typeOfT);
        }else{
            return Collections.EMPTY_LIST;
        }
    }
}

package com.sovegetables.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class NotNullStringDeserializer implements JsonDeserializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String asString = json.getAsString();
            if(asString == null){
                return "";
            }
            return asString;
        } catch (Exception e) {
            return "";
        }
    }
}

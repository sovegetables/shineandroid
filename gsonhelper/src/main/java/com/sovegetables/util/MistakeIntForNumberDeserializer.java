package com.sovegetables.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MistakeIntForNumberDeserializer implements JsonDeserializer<Number> {

    @Override
    public Number deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String asString = json.getAsString();
            if(asString.equalsIgnoreCase("true")){
                return 1;
            }
            if(asString.equalsIgnoreCase("false")){
                return 0;
            }
            return json.getAsInt();
        } catch (Exception e) {
            return 0;
        }
    }
}

package com.sovegetables.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MistakeBooleanDeserializer implements JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json != null){
            try {
                if(json.getAsString().equals("1")){
                    return true;
                }

                Integer integer;
                try {
                    integer = Integer.valueOf(json.getAsString());
                    return integer > 0;
                } catch (NumberFormatException ignored) {
                }
                return json.getAsBoolean();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}

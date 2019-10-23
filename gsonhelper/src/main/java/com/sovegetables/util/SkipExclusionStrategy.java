package com.sovegetables.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class SkipExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(SkipSerialize.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(SkipSerialize.class) != null;
    }
}

package ru.yawlick.brownylib.common.content.data.impl;

import lombok.Getter;
import ru.yawlick.brownylib.api.content.data.Value;

import java.util.List;

public class ValueImpl implements Value {
    private String originalValue;

    public ValueImpl(String originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public void set(Object object) {
        originalValue = String.valueOf(object);
    }

    @Override
    public String asString() {
        return originalValue;
    }

    @Override
    public int asInteger() {
        return Integer.parseInt(originalValue);
    }

    @Override
    public float asFloat() {
        return Float.parseFloat(originalValue);
    }

    @Override
    public double asDouble() {
        return Double.parseDouble(originalValue);
    }

    @Override
    public long asLong() {
        return Long.parseLong(originalValue);
    }

    @Override
    public String[] asArray() {
        return originalValue.split(";");
    }

    @Override
    public <T extends Object> T asType(T type) {
        try {
            return (T) originalValue;
        } catch (Exception e) {
            warn("Error while trying to cast Value: " + e.getMessage());
            return null;
        }
    }
}

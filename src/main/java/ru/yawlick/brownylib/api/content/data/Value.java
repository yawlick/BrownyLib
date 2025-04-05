package ru.yawlick.brownylib.api.content.data;

import ru.yawlick.brownylib.api.IFastBrowny;

import java.util.HashMap;
import java.util.List;

public interface Value extends IFastBrowny {
    void set(Object object);

    String asString();

    int asInteger();

    float asFloat();

    double asDouble();

    long asLong();

    String[] asArray();

    <T extends Object> T asType(T type);
}

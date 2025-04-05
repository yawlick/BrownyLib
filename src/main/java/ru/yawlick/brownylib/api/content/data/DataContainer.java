package ru.yawlick.brownylib.api.content.data;

import ru.yawlick.brownylib.api.IFastBrowny;

import java.util.HashMap;

public interface DataContainer extends IFile, IFastBrowny {
    Value get(String key);

    DataContainer set(String key, Object value);

    void save();
}

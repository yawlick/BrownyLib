package ru.yawlick.brownylib.api.content;

import ru.yawlick.brownylib.api.IFastBrowny;

public interface Module extends IFastBrowny {
    void load();

    void unload();
}

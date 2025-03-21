package ru.yawlick.brownylib.common.content;

import ru.yawlick.brownylib.api.content.Module;

public abstract class AbstractModule implements Module {
    @Override
    public abstract void load();

    @Override
    public abstract void unload();
}

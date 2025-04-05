package ru.yawlick.brownylib.api.content.data;

import ru.yawlick.brownylib.api.IFastBrowny;

import java.io.File;

public interface DataStore extends IFolder, IFastBrowny {
    void save();
}

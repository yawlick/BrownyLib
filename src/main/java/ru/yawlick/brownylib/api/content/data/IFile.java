package ru.yawlick.brownylib.api.content.data;

import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.common.content.data.impl.DataContainerImpl;
import ru.yawlick.brownylib.common.content.data.impl.DataGroupImpl;
import ru.yawlick.brownylib.common.content.data.impl.DataStoreImpl;

import java.io.File;

public interface IFile extends IFastBrowny {
    File getFile();

    IFolder getParent();

    String getName();

    abstract void load();

    void renameTo(String newName);

    void save();

    boolean isDataStore();

    boolean isGroup();

    boolean isContainer();
}

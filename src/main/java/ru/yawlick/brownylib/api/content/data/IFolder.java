package ru.yawlick.brownylib.api.content.data;

import ru.yawlick.brownylib.common.content.data.impl.DataGroupImpl;

import java.io.File;
import java.util.ArrayList;

public interface IFolder extends IFile {
    ArrayList<IFile> getLoadedChilds();

    DataContainer getContainer(String name);

    DataGroup getGroup(String name);
}

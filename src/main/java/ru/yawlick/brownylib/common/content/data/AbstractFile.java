package ru.yawlick.brownylib.common.content.data;

import lombok.Getter;
import ru.yawlick.brownylib.api.content.data.IFile;
import ru.yawlick.brownylib.api.content.data.IFolder;
import ru.yawlick.brownylib.common.content.data.impl.DataContainerImpl;
import ru.yawlick.brownylib.common.content.data.impl.DataGroupImpl;
import ru.yawlick.brownylib.common.content.data.impl.DataStoreImpl;

import java.io.File;

@Getter
public abstract class AbstractFile implements IFile {
    private IFolder parent;
    private String name;
    private File file;

    public AbstractFile(IFolder parent, File file, String name) {
        this.parent = parent;
        this.file = file;
        this.name = name;
    }

    public abstract void load();

    @Override
    public IFolder getParent() {
        return this.parent;
    }

    public void renameTo(String newName) {
        if(isDataStore()) {
            warn("The DataStore can not be renamed");
        } if(isGroup()) {
            file.renameTo(file.getParentFile().toPath().resolve(newName).toFile());
        } else {
            file.renameTo(file.getParentFile().toPath().resolve(newName + ".json").toFile());
        }
    }

    public boolean isContainer() {
        return this instanceof DataContainerImpl;
    }

    public boolean isGroup() {
        return this instanceof DataGroupImpl;
    }

    public boolean isDataStore() {
        return this instanceof DataStoreImpl;
    }
}

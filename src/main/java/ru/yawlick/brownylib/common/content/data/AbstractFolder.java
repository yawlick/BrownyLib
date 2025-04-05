package ru.yawlick.brownylib.common.content.data;

import lombok.Getter;
import ru.yawlick.brownylib.api.content.data.DataContainer;
import ru.yawlick.brownylib.api.content.data.DataGroup;
import ru.yawlick.brownylib.api.content.data.IFile;
import ru.yawlick.brownylib.api.content.data.IFolder;
import ru.yawlick.brownylib.common.content.data.impl.DataContainerImpl;
import ru.yawlick.brownylib.common.content.data.impl.DataGroupImpl;
import ru.yawlick.brownylib.common.content.data.impl.DataStoreImpl;

import java.io.File;
import java.util.ArrayList;

@Getter
public abstract class AbstractFolder extends AbstractFile implements IFolder {
    private final ArrayList<IFile> loadedChilds;

    public AbstractFolder(IFolder parent, File file, String name) {
        super(parent, file, name);
        this.loadedChilds = new ArrayList<>();
    }

    public abstract void load();

    @Override
    public DataGroup getGroup(String name) {
        String groupName = name.replace(" ", "-");
        for(IFile child : getLoadedChilds()) {
            if(child.isGroup()) {
                DataGroup group = ((DataGroup) child);
                if(group.getName().equals(groupName))
                    return group;
            }
        }

        File folder = getFile().toPath().resolve("DG_" + groupName).toFile();
        DataGroup dataGroup = new DataGroupImpl(this, groupName, folder);
        dataGroup.load();
        return dataGroup;
    }

    @Override
    public DataContainer getContainer(String name) {
        for(IFile child : getLoadedChilds()) {
            if(child.isContainer()) {
                return (DataContainer) child;
            }
        }

        DataContainer container = new DataContainerImpl(this, name, getFile().toPath().resolve(name + ".json").toFile());
        container.load();
        return container;
    }

    public boolean isContainer() {
        return false;
    }

    public boolean isGroup() {
        return this instanceof DataGroupImpl;
    }

    public boolean isDataStore() {
        return this instanceof DataStoreImpl;
    }
}

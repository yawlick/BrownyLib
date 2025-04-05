package ru.yawlick.brownylib.common.content.data.impl;

import lombok.Getter;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.data.*;
import ru.yawlick.brownylib.common.content.data.AbstractFolder;

import java.io.File;

@Getter
public class DataStoreImpl extends AbstractFolder implements DataStore {
    public DataStoreImpl(String name, File folder) {
        super(null, folder, name);
    }

    public void load() {
        if(!BrownyLib.getInstance().getDataModule().getLoadedDataStoreList().contains(this)) {
            if(!getFile().exists()) {
                getFile().mkdirs();
            } else if(!getFile().isDirectory()) {
                getFile().delete();
                getFile().mkdirs();
            }

            BrownyLib.getInstance().getDataModule().getLoadedDataStoreList().add(this);
            log("Successfully loaded DataStore for plugin '%s'", getName());
        } else {
            log("Can not load DataStore for plugin '%s', because he is already loaded!", getName());
        }
    }

    @Override
    public void save() {
        log("Saving DataStore '%s'", getName());
        for(IFile child : getLoadedChilds()) {
            log("DataStore child '%s', isGroup: %s", child.getName(), child.isGroup());
            if(child.isGroup()) {
                ((DataGroup) child).save();
            }
        }
    }

    @Override
    public IFolder getParent() {
        return null;
    }

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
}

package ru.yawlick.brownylib.common.content.data.impl;

import lombok.Getter;
import ru.yawlick.brownylib.api.content.data.DataGroup;
import ru.yawlick.brownylib.api.content.data.DataContainer;
import ru.yawlick.brownylib.api.content.data.IFile;
import ru.yawlick.brownylib.api.content.data.IFolder;
import ru.yawlick.brownylib.common.content.data.AbstractFile;
import ru.yawlick.brownylib.common.content.data.AbstractFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DataGroupImpl extends AbstractFolder implements DataGroup {
    public DataGroupImpl(IFolder parent, String name, File folder) {
        super(parent, folder, name);
    }

    public void load() {
        if(!getParent().getLoadedChilds().contains(this)) {
            if(!getFile().exists()) {
                getFile().mkdirs();
            } else if(!getFile().isDirectory()) {
                getFile().delete();
                getFile().mkdirs();
            }

            for(File f : getFile().listFiles()) {
                if(f.getName().startsWith("DG_")) {
                    DataGroup dataGroup = new DataGroupImpl(this, f.getName().replace("DG_", ""), f);
                    dataGroup.load();
                }
                if(f.getName().endsWith(".json")) {
                    DataContainer container = new DataContainerImpl(this, f.getName().replace(".json", ""), f);
                    container.load();
                }
            }

            getParent().getLoadedChilds().add(this);
            log("Successfully loaded DataGroup with name '%s'", getName());
        } else {
            log("Can not load DataGroup with a name '%s', because he is already loaded!", getName());
        }
    }

    @Override
    public void save() {
        log("Saving DataGroup '%s'", getName());
        for(IFile child : getLoadedChilds()) {
            if(child.isGroup()) {
                ((DataGroup) child).save();
            }
            if(child.isContainer()) {
                ((DataContainer) child).save();
            }
        }
    }


}

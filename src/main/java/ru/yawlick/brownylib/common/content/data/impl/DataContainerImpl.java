package ru.yawlick.brownylib.common.content.data.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.data.*;
import ru.yawlick.brownylib.common.content.data.AbstractFile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DataContainerImpl extends AbstractFile implements DataContainer {
    private final HashMap<String, Value> dataMap;

    public DataContainerImpl(IFolder parent, String name, File file) {
        super(parent, file, name);
        this.dataMap = new HashMap<>();
    }

    public void load() {
        if(!getParent().getLoadedChilds().contains(this)) {
            try {
                if(!getFile().exists()) {
                    getFile().createNewFile();
                } else if(getFile().isDirectory()) {
                    getFile().delete();
                    getFile().createNewFile();
                }
            } catch (Exception ignored) {}

            readJson();

            getParent().getLoadedChilds().add(this);
            log("Successfully loaded DataContainer with name '%s'", getName());
        } else {
            log("Can not load DataContainer with a name '%s', because he is already loaded!", getName());
        }
    }

    private void readJson() {
        try {
            Gson gson = BrownyLib.getInstance().getDataModule().getGson();
            FileReader fileReader = new FileReader(getFile().getAbsoluteFile());

            if (getFile().length() == 0) {
                warn("Empty JSON file of the data container (%s)", getFile());
                return;
            }

            dataMap.clear();
            Type mapType = new TypeToken<HashMap<String, String>>() {}.getType();
            Object parsed = gson.fromJson(fileReader, Object.class);
            if (parsed instanceof Map) {
                HashMap<String, String> map = gson.fromJson(gson.toJson(parsed), mapType);
                for(String key : map.keySet()) {
                    dataMap.put(key, new ValueImpl(map.get(key)));
                }
            } else {
                warn("Unknown JSON format in the data container (%s)", getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Value get(String key) {
        return dataMap.get(key);
    }

    @Override
    public DataContainer set(String key, Object value) {
        if(dataMap.containsKey(key)) {
            dataMap.get(key).set(value);
        } else {
            Value valueInstance = new ValueImpl(String.valueOf(value));
            dataMap.put(key, valueInstance);
        }

        return this;
    }

    @Override
    public void save() {
        log("Saving DataContainer '%s', parent is '%s'", getName(), getParent().getName());
        try {
            Gson gson = BrownyLib.getInstance().getDataModule().getGson();
            if(!getFile().exists())
                getFile().createNewFile();

            // TODO: Узнать и пофиксит почему ничего не записывается в жсон
            // TODO #2: Скачать тёмную тему для блокнота
            HashMap<String, String> map = new HashMap<>();
            for(String key : dataMap.keySet()) {
                map.put(key, dataMap.get(key).asString());
            }

            FileWriter fileWriter = new FileWriter(getFile().getAbsoluteFile());
            gson.toJson(map, fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

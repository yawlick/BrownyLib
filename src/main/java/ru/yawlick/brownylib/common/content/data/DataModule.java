package ru.yawlick.brownylib.common.content.data;

import com.google.gson.Gson;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.yawlick.brownylib.api.content.data.DataStore;
import ru.yawlick.brownylib.api.content.data.IDataModule;
import ru.yawlick.brownylib.common.config.ConfigManager;
import ru.yawlick.brownylib.common.content.AbstractModule;
import ru.yawlick.brownylib.common.content.data.impl.DataStoreImpl;

import java.io.File;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public final class DataModule extends AbstractModule implements IDataModule {
    private ArrayList<DataStore> loadedDataStoreList;
    private Gson gson;

    public DataModule() {
        loadedDataStoreList = new ArrayList<>();
        gson = new Gson();
    }

    @Override
    public void load() {
        File dataFolder = ConfigManager.DATA_DIR;
        log("Trying to load DataStore(s) from the plugin dir..");
        ArrayList<DataStore> dataStoreList = getDataStoreList(dataFolder);
        if(dataStoreList.isEmpty()) {
            log("No one DataStore hasn't been founded, sadly =(");
        } else {
            for(DataStore dataStore : dataStoreList) {
                dataStore.load();
            }
        }
    }

    public DataStore getDataStore(JavaPlugin javaPlugin) {
        String name = javaPlugin.getName().toLowerCase().replace(" ", "-");
        for(DataStore dataStore : loadedDataStoreList) {
            if(dataStore.getName().equals(name)) {
                return dataStore;
            }
        }

        File folder = ConfigManager.DATA_DIR.toPath().resolve("DS_" + name).toFile();
        DataStore dataStore = new DataStoreImpl(name, folder);
        dataStore.load();
        return dataStore;
    }

    private ArrayList<DataStore> getDataStoreList(File dataFolder) {
        if(dataFolder.exists()) {
            if(dataFolder.listFiles() == null) {
                return new ArrayList<>();
            } else {
                ArrayList<DataStore> list = new ArrayList<>();
                HashMap<String, File> map = new HashMap<>();
                for(File folder : dataFolder.listFiles()) {
                    if(folder.isDirectory() && folder.getName().startsWith("DS_")) {
                        map.put(folder.getName().replace("DS_", ""), folder);
                    }
                }

                for(String name : map.keySet()) {
                    list.add(new DataStoreImpl(name, map.get(name)));
                }

                return list;
            }
        } else {
            dataFolder.mkdirs();
            return getDataStoreList(dataFolder);
        }
    }

    @Override
    public void unload() {
        for(DataStore dataStore : loadedDataStoreList) {
            dataStore.save();
        }
        loadedDataStoreList.clear();
    }
}
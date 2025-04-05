package ru.yawlick.brownylib.api.content.data;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;

import java.io.File;
import java.util.ArrayList;

public interface IDataModule {
    DataStore getDataStore(JavaPlugin name);

    ArrayList<DataStore> getLoadedDataStoreList();

    Gson getGson();
}

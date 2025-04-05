package ru.yawlick.brownylib.common.config;

import java.io.File;

public final class ConfigManager {
    public static File PLUGIN_DIR;
    public static File CONFIG_JSON;
    public static File DATA_DIR;

    public void setup(File pluginFolder) {
        PLUGIN_DIR = pluginFolder;
        CONFIG_JSON = pluginFolder.toPath().resolve("config.json").toFile();
        DATA_DIR = pluginFolder.toPath().resolve("DATA").toFile();

        try {
            for(File dir : new File[]{PLUGIN_DIR, DATA_DIR}) {
                if(!dir.exists())
                    dir.mkdirs();
            }

            for(File file : new File[]{CONFIG_JSON}) {
                if(!file.exists())
                    file.createNewFile();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

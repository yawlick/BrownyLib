package ru.yawlick.brownylib.api.content.menu;

import org.bukkit.plugin.java.JavaPlugin;
import ru.yawlick.brownylib.api.content.event.EventTracker;

import java.util.ArrayList;
import java.util.List;

public interface IMenuModule {
    ArrayList<Menu> getMenuList();

    Menu get(String id);

    void registerMenu(Menu menu);
}
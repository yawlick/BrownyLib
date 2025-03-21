package ru.yawlick.brownylib.common.content.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.event.EventTracker;
import ru.yawlick.brownylib.api.content.event.IEventModule;
import ru.yawlick.brownylib.common.content.AbstractModule;
import ru.yawlick.brownylib.common.content.event.inventory.InventoryClickEventTracker;
import ru.yawlick.brownylib.common.content.event.player.*;
import ru.yawlick.brownylib.common.content.event.tick.ServerTickEventTracker;
import ru.yawlick.brownylib.common.content.event.world.BlockFadeEventTracker;
import ru.yawlick.brownylib.common.content.event.world.BlockInteractEventTracker;
import ru.yawlick.brownylib.common.content.event.world.BlockPhysicsEventTracker;

import java.util.Arrays;
import java.util.List;

@Getter
public final class EventModule extends AbstractModule implements IEventModule {
    private final PluginManager pluginManager;

    public EventModule() {
        pluginManager = Bukkit.getServer().getPluginManager();
        load();
    }

    public <T extends EventTracker> void registerListener(T eventTracker, JavaPlugin plugin) {
        pluginManager.registerEvents(eventTracker, plugin);
    }

    public <T extends EventTracker> void registerListeners(List<T> eventTrackerList, JavaPlugin plugin) {
        for(T eventTracker : eventTrackerList) {
            pluginManager.registerEvents(eventTracker, plugin);
        }
    }

    @Override
    public void load() {
        List<EventTracker> eventTrackerList = Arrays.asList(
                new InventoryClickEventTracker(),

                new PlayerChangedWorldEventTracker(),
                new PlayerJoinEventTracker(),
                new PlayerQuitEventTracker(),

                new ServerTickEventTracker(),

                new BlockPhysicsEventTracker(),
                new BlockFadeEventTracker(),
                new BlockInteractEventTracker()
        );
        registerListeners(eventTrackerList, BrownyLib.getInstance());
    }

    @Override
    public void unload() {}
}
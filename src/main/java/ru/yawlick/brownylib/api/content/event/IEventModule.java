package ru.yawlick.brownylib.api.content.event;

import org.bukkit.plugin.java.JavaPlugin;
import ru.yawlick.brownylib.api.content.command.Command;

import java.util.ArrayList;
import java.util.List;

public interface IEventModule {
    <T extends EventTracker> void registerListener(T eventTracker, JavaPlugin plugin);

    <T extends EventTracker> void registerListeners(List<T> eventTrackerList, JavaPlugin plugin);
}
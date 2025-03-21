package ru.yawlick.brownylib.api.content.command;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public interface ICommandModule {
    Command get(String name);

    ArrayList<Command> getCommands();

    void registerCommand(JavaPlugin plugin, Command command);

    void registerCommands(JavaPlugin plugin, ArrayList<Command> commands);
}

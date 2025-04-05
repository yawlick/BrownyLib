package ru.yawlick.brownylib.common.content.command;

import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.command.Command;
import ru.yawlick.brownylib.api.content.command.ICommandModule;
import ru.yawlick.brownylib.common.content.AbstractModule;
import ru.yawlick.brownylib.common.content.command.impl.*;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public final class CommandModule extends AbstractModule implements ICommandModule {
    private final ArrayList<Command> commands = new ArrayList<>();

    public CommandModule() {
        commands.addAll(Arrays.asList(
                new WorldCommand(true),
                new HextestCommand(true)
        ));
        load();
    }

    public Command get(String name) {
        for (Command command : commands) {
            if (command != null && command.getCommand().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public void registerCommand(JavaPlugin plugin, Command command) {
        PluginCommand pluginCommand = plugin.getCommand(command.getCommand());
        if(pluginCommand == null) {
            log(String.format("The '%s' command is null!", command.getCommand()));
            return;
        }
        plugin.getCommand(command.getCommand()).setExecutor(command);
        plugin.getCommand(command.getCommand()).setTabCompleter(command);
        plugin.getCommand(command.getCommand()).setAliases(command.getAliases());
        ((AbstractCommand) command).registered = true;
        log(String.format("The '%s' command has been successfully registered", command.getUsage()));
    }

    public void registerCommands(JavaPlugin plugin, ArrayList<Command> commands) {
        for(Command command : commands) {
            if(!command.isRegistered())
                registerCommand(plugin, command);
        }
    }

    @Override
    public void load() {
        registerCommands(BrownyLib.getInstance(), commands);
    }

    @Override
    public void unload() {}
}

package ru.yawlick.brownylib.api.content.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import ru.yawlick.brownylib.api.IFastBrowny;

import java.util.List;

public interface Command extends TabCompleter, CommandExecutor, IFastBrowny {
    String getCommand();

    String getDescription();

    String getUsage();

    List<String> getAliases();

    boolean isRegistered();

    boolean isEnabled();

    boolean isOnlyForPlayers();

    void setEnabled(boolean value);

    void setOnlyForPlayers(boolean value);
}

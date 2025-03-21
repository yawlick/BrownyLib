package ru.yawlick.brownylib.common.content.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.yawlick.brownylib.api.content.command.Command;
import ru.yawlick.brownylib.common.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractCommand implements Command {
    protected final String command;
    protected final String description;
    protected final String usage;
    protected final List<String> aliases;
    protected boolean registered = false;
    @Setter protected boolean enabled = true;
    @Setter protected boolean onlyForPlayers = false;

    public AbstractCommand(String command, String description, String usage, List<String> aliases) {
        this.command = command;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if(onlyForPlayers) {
            if(!PlayerUtil.isPlayer(commandSender))
                return false;
        }
        return onCommand(commandSender, PlayerUtil.isPlayer(commandSender) ? (CraftPlayer) PlayerUtil.getPlayer(commandSender) : null, command, new Args(strings));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if(onlyForPlayers) {
            if(!PlayerUtil.isPlayer(commandSender))
                return new ArrayList();
        }
        return onTabComplete(commandSender, PlayerUtil.isPlayer(commandSender) ? (CraftPlayer) PlayerUtil.getPlayer(commandSender) : null, command, new Args(strings));
    }

    public abstract boolean onCommand(CommandSender sender, CraftPlayer player, org.bukkit.command.Command command, Args args);

    public abstract List<String> onTabComplete(CommandSender sender, CraftPlayer player, org.bukkit.command.Command command, Args args);
}

package ru.yawlick.brownylib.common.content.command.impl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import ru.yawlick.brownylib.common.content.command.AbstractCommand;
import ru.yawlick.brownylib.common.content.command.Args;
import ru.yawlick.brownylib.common.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class HextestCommand extends AbstractCommand {
    private static final String localCommand = "hextest";
    private static final String localDescription = "";
    private static final String localUsage = "/hextest <any)";
    private static final List<String> localAliases = new ArrayList<>();

    public HextestCommand(boolean state) {
        super(localCommand, localDescription, localUsage, localAliases);
        onlyForPlayers = true;
        enabled = state;
    }

    @Override
    public boolean onCommand(CommandSender sender, CraftPlayer player, Command command, Args args) {
        player.sendMessage(TextUtil.formatText(args.all()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, CraftPlayer player, Command command, Args args) {
        return new ArrayList<>();
    }
}

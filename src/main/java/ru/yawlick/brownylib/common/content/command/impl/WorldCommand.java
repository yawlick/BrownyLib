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

import java.util.ArrayList;
import java.util.List;

public class WorldCommand extends AbstractCommand {
    private static final String localCommand = "world";
    private static final String localDescription = "Телепортирует вас в мир";
    private static final String localUsage = "/world <world_name>";
    private static final List<String>localAliases = new ArrayList<>();

    public WorldCommand(boolean state) {
        super(localCommand, localDescription, localUsage, localAliases);
        onlyForPlayers = true;
        enabled = state;
    }

    @Override
    public boolean onCommand(CommandSender sender, CraftPlayer player, Command command, Args args) {
        if(args.first() != null && Bukkit.getWorld(args.first()) != null) {
            player.teleport(new Location(Bukkit.getWorld(args.first()), 0.5, 100, 0.5));
            if(player.getGameMode() == GameMode.CREATIVE)
                player.setFlying(true);
        } else {
            msg(player, "§cУкажите правильное имя мира! §7(" + localUsage + ")");
            String worlds = "Созданные миры: §6";
            for(World world : Bukkit.getWorlds()) {
                if(!world.getName().equalsIgnoreCase("world") && !world.getName().equalsIgnoreCase("world_nether") && !world.getName().equalsIgnoreCase("world_the_end"))
                    worlds += world.getName() + "; ";
            }
            msg(player, worlds);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, CraftPlayer player, Command command, Args args) {
        ArrayList<String> worlds = new ArrayList<>();
        for(World world : Bukkit.getWorlds()) {
            if(!world.getName().equalsIgnoreCase("world") && !world.getName().equalsIgnoreCase("world_nether") && !world.getName().equalsIgnoreCase("world_the_end"))
                worlds.add(world.getName());
        }
        return worlds;
    }
}

package ru.yawlick.brownylib.common.util;

import net.minecraft.network.protocol.Packet;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.BrownyLib;

import java.util.UUID;

public final class PlayerUtil {

    public static boolean isPlayer(CommandSender commandSender) {
        if(commandSender instanceof Player) {
            return true;
        }
        return false;
    }

    public static Player getPlayer(CommandSender commandSender) {
        if(commandSender instanceof Player) {
            return (Player) commandSender;
        }
        return null;
    }

    public static Player getPlayer(String name) {
        Player player = null;
        for(Player player1 : Bukkit.getOnlinePlayers()) {
            if(player1.getName().equalsIgnoreCase(name)) {
                player = player1;
                break;
            }
        }
        return player;
    }

    public static Player getPlayer(UUID uuid) {
        Player player = null;
        for(Player player1 : Bukkit.getOnlinePlayers()) {
            if(player1.getUniqueId().equals(uuid)) {
                player = player1;
                break;
            }
        }
        return player;
    }

    public static void playSound(Player player, Sound sound) {
        playSound(player, sound, SoundCategory.MASTER);
    }

    public static void playSound(Player player, Sound sound, int i) {
        for(;i > 0; i--) {
            playSound(player, sound, SoundCategory.MASTER);
        }
    }

    public static void playSound(Player player, Sound sound, SoundCategory soundCategory) {
        player.playSound(player.getLocation(), sound, soundCategory, 1f, 1f);
    }

    public static void msg(String text) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(TextUtil.formatText(BrownyLib.PREFIX + text));
        }
    }

    public static void msg(Player player, String text) {
        player.sendMessage(BrownyLib.PREFIX + text);
    }

    public static void sendPacket(Player player, Packet<?> packet) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().connection.sendPacket(packet);
    }
}

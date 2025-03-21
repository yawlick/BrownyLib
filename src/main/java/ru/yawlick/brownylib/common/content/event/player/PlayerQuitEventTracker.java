package ru.yawlick.brownylib.common.content.event.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.event.EventTracker;

public class PlayerQuitEventTracker implements EventTracker {
    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.ADVENTURE) {
            player.getInventory().clear();
        }
        for(Player player2 : Bukkit.getOnlinePlayers()) {
            player2.sendMessage(" §7[§c§l-§r§7] §c" + player.getName());
        }
    }
}

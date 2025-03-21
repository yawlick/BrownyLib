package ru.yawlick.brownylib.common.content.event.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;
import ru.yawlick.brownylib.api.content.event.EventTracker;

public class PlayerChatEventTracker implements EventTracker {
    @EventHandler(priority = EventPriority.HIGHEST)
    void onPlayerChatting(PlayerChatEvent event) {
        Player chatter = event.getPlayer();
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(" §r" + chatter.getDisplayName() + " §7§l» §r§a§f" + event.getMessage());

//            if(getRankType(chatter).isStaff()) {
//            } else if (getRankType(chatter) != RankType.PLAYER){
//                player.sendMessage(chatter.getDisplayName() + " §7§l» §r§f" + event.getMessage());
//            } else {
//                player.sendMessage(chatter.getDisplayName() + " §7§l» §r§7" + event.getMessage());
//            }
        }
        event.setCancelled(true);
    }
}
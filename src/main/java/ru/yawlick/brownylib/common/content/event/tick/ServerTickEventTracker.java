package ru.yawlick.brownylib.common.content.event.tick;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import ru.yawlick.brownylib.api.content.event.EventTracker;

public class ServerTickEventTracker implements EventTracker {
    @EventHandler
    void onTickPre(ServerTickStartEvent event) {
    }

    @EventHandler
    void onTickPost(ServerTickEndEvent event) {
        for(World world : Bukkit.getWorlds()) {
            for(Player player : world.getPlayers()) {
                CraftPlayer craftPlayer = (CraftPlayer) player;
                craftPlayer.sendActionBar("§6" + world.getName());
            }
        }
    }
}
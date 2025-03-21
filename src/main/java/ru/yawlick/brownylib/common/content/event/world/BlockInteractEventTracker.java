package ru.yawlick.brownylib.common.content.event.world;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.yawlick.brownylib.api.content.event.EventTracker;

public class BlockInteractEventTracker implements EventTracker {
    @EventHandler
    public void onBlockFade(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block != null && block.getType() == Material.IRON_TRAPDOOR) {
            Openable openable = (Openable) block.getBlockData();

            if (openable.isOpen()) {
                openable.setOpen(false);
                block.setBlockData(openable);
            } else {
                openable.setOpen(true);
                block.setBlockData(openable);
            }
        }
    }
}

package ru.yawlick.brownylib.common.content.event.world;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFadeEvent;
import ru.yawlick.brownylib.api.content.event.EventTracker;

public class BlockFadeEventTracker implements EventTracker {
    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        Block block = event.getBlock();

        if (block.getType().toString().contains("CORAL_BLOCK") || block.getType().toString().contains("CORAL")) {
            event.setCancelled(true);
        }
    }
}

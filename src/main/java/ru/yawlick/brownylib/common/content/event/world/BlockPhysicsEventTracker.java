package ru.yawlick.brownylib.common.content.event.world;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import ru.yawlick.brownylib.api.content.event.EventTracker;

public class BlockPhysicsEventTracker implements EventTracker {
    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if(event.getBlock().getType() == Material.CHEST)
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onSandFall(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().name().equals("FALLING_BLOCK")) {
            FallingBlock fallingBlock = (FallingBlock) entity;
            event.getBlock().setType(fallingBlock.getMaterial());
            event.setCancelled(true);
        }
    }
}

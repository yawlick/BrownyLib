package ru.yawlick.brownylib.common.content.event.player;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.api.content.event.EventTracker;
import ru.yawlick.brownylib.common.content.entity.impl.CustomNPCEntity;

import java.util.Arrays;

public class PlayerChangedWorldEventTracker implements EventTracker {
    @EventHandler
    void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        for(CustomEntity entity : BrownyLib.getInstance().getCustomEntityModule().getEntities()) {
            if(entity instanceof CustomNPCEntity) {
                ServerPlayer serverPlayer = ((CustomNPCEntity) entity).getNPC();
                ClientboundPlayerInfoRemovePacket removePlayerInfoPacket = new ClientboundPlayerInfoRemovePacket(
                        Arrays.asList(serverPlayer.getUUID())
                );
                ClientboundRemoveEntitiesPacket destroyPacket = new ClientboundRemoveEntitiesPacket(
                        serverPlayer.getId()
                );

                ((CraftPlayer) player).getHandle().connection.sendPacket(removePlayerInfoPacket);
                ((CraftPlayer) player).getHandle().connection.sendPacket(destroyPacket);
            }

            if(entity instanceof CustomNPCEntity && entity.getEntity().getWorld().getPlayers().contains(player)) {
                ((CustomNPCEntity) entity).sendPackets(player);
            }
        }
    }
}

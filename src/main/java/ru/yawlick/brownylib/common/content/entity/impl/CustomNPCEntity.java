package ru.yawlick.brownylib.common.content.entity.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.common.content.entity.AbstractCustomEntity;
import ru.yawlick.brownylib.common.util.SkinList;

import java.util.UUID;

@Getter
public class CustomNPCEntity extends AbstractCustomEntity {
    private final ServerPlayer NPC;

    public CustomNPCEntity(Location location, String name) {
        this(
                new ServerPlayer(
                        ((CraftServer) Bukkit.getServer()).getServer(),
                        ((CraftWorld) location.getWorld()).getHandle(),
                        new GameProfile(UUID.randomUUID(), name),
                        ClientInformation.createDefault()
                ),
                location
        );
    }

    private CustomNPCEntity(ServerPlayer serverPlayer, Location location) {
        super(serverPlayer.getBukkitEntity());
        this.NPC = serverPlayer;
        serverPlayer.setInvulnerable(true);
        serverPlayer.setPos(location.getX(), location.getY(), location.getZ());

        GameProfile profile = serverPlayer.getGameProfile();
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", new Property("textures", SkinList.DEFAULT.getTexture(), SkinList.DEFAULT.getSign()));

        for(Player player : serverPlayer.getBukkitEntity().getWorld().getPlayers()) {
            sendPackets(player);
        }
    }

    public void sendPackets(Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.sendPacket(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, NPC));
        connection.sendPacket(new ClientboundAddEntityPacket(
                NPC.getId(), NPC.getUUID(), NPC.getX(), NPC.getY(), NPC.getZ(), NPC.getXRot(), NPC.getYRot(),
                NPC.getType(), 0, NPC.getDeltaMovement(), NPC.yHeadRot
        ));
        connection.sendPacket(new ClientboundRotateHeadPacket(NPC, (byte) (NPC.getBukkitYaw() * 256 / 360)));
    }
}

package ru.yawlick.brownylib.api.content.network;

import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;

public interface IPacketEvent<E extends Packet> {
    E getPacket();

    Player getPlayer();

    long getWhenReceived();
}

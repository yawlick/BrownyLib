package ru.yawlick.brownylib.common.content.network;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.api.content.network.IPacketEvent;

@Getter
public final class WrappedPacket<E extends Packet> implements IPacketEvent {
    private final E packet;
    private final Player player;
    private final long whenReceived;

    public WrappedPacket(E packet, Player player, long whenReceived) {
        this.packet = packet;
        this.player = player;
        this.whenReceived = whenReceived;
    }
}
package ru.yawlick.brownylib.common.content.network;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import org.bukkit.plugin.Plugin;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.api.content.network.INetworkModule;
import ru.yawlick.brownylib.common.content.AbstractModule;
import ru.yawlick.brownylib.common.content.entity.event.EntityEvent;

import java.util.*;
import java.util.function.Consumer;

public final class NetworkModule extends AbstractModule implements INetworkModule {
    private final Map<Class<? extends Packet>, Set<Consumer<WrappedPacket>>> sendingConsumers = new HashMap<>();
    private final Map<Class<? extends Packet>, Set<Consumer<WrappedPacket>>> receivingConsumers = new HashMap<>();

    public NetworkModule() {
        // TODO: Сделать что бы PacketListener из PrtocolLib'а реально читал пакеты
        //load();
    }

    @Override
    public void load() {
        ArrayList<PacketType> whitelist = new ArrayList<>();
        for(PacketType packetType : PacketType.values()) {
            whitelist.add(packetType);
        }
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener() {
            @Override
            public void onPacketSending(PacketEvent packetEvent) {
                fireSending(new WrappedPacket<>((Packet) packetEvent.getPacket().getHandle(), packetEvent.getPlayer(), System.currentTimeMillis()));
            }

            @Override
            public void onPacketReceiving(PacketEvent packetEvent) {
                fireReceiving(new WrappedPacket<>((Packet) packetEvent.getPacket().getHandle(), packetEvent.getPlayer(), System.currentTimeMillis()));
            }

            @Override
            public ListeningWhitelist getReceivingWhitelist() {
                return ListeningWhitelist.newBuilder().types(whitelist).build();
            }

            @Override
            public ListeningWhitelist getSendingWhitelist() {
                return ListeningWhitelist.newBuilder().types(whitelist).build();
            }

            @Override
            public Plugin getPlugin() {
                return BrownyLib.getInstance();
            }
        });

        registerSending(ClientboundAddEntityPacket.class, (packet) -> {
            log("Adding new entity for player %s", packet.getPlayer().getName());
        });

        registerReceiving(ServerboundSignUpdatePacket.class, (packet) -> {
            log("Player %s has changed the sign: %s", packet.getPlayer().getName(), Arrays.toString(packet.getPacket().getLines()));
        });
    }

    /// -# Packet sending #-
    public <E extends Packet> void registerSending(Class<E> packet, Consumer<WrappedPacket<E>> consumer) {
        if(packet != null && consumer != null) {
            Set<Consumer<WrappedPacket>> eventsSet = sendingConsumers.computeIfAbsent(packet, (k) -> {
                return new HashSet();
            });
            eventsSet.add((Consumer<WrappedPacket>) (((Object)consumer)));
        }
    }

    public <E extends Packet> void fireSending(WrappedPacket<E> packet) {
        Set<Consumer<WrappedPacket>> eventsSet = sendingConsumers.get(packet.getClass());
        if (eventsSet != null) {
            Iterator var3 = eventsSet.iterator();

            while(var3.hasNext()) {
                Consumer<WrappedPacket> consumer = (Consumer<WrappedPacket>) var3.next();
                consumer.accept(packet);
            }
        }
    }

    /// -# Packet receiving #-
    public <E extends Packet> void registerReceiving(Class<E> packet, Consumer<WrappedPacket<E>> consumer) {
        if(packet != null && consumer != null) {
            Set<Consumer<WrappedPacket>> eventsSet = receivingConsumers.computeIfAbsent(packet, (k) -> {
                return new HashSet();
            });
            eventsSet.add((Consumer<WrappedPacket>) (((Object)consumer)));
        }
    }

    public <E extends Packet> void fireReceiving(WrappedPacket<E> packet) {
        Set<Consumer<WrappedPacket>> eventsSet = receivingConsumers.get(packet.getClass());
        if (eventsSet != null) {
            Iterator var3 = eventsSet.iterator();

            while(var3.hasNext()) {
                Consumer<WrappedPacket> consumer = (Consumer<WrappedPacket>) var3.next();
                consumer.accept(packet);
            }
        }
    }

    @Override
    public void unload() {}
}

package ru.yawlick.brownylib.api.content.network;

import net.minecraft.network.protocol.Packet;
import ru.yawlick.brownylib.api.content.menu.Menu;
import ru.yawlick.brownylib.common.content.network.WrappedPacket;

import java.util.function.Consumer;

public interface INetworkModule {
    <E extends Packet> void registerSending(Class<E> packet, Consumer<WrappedPacket<E>> consumer);

    <E extends Packet> void fireSending(WrappedPacket<E> packet);

    <E extends Packet> void registerReceiving(Class<E> packet, Consumer<WrappedPacket<E>> consumer);

    <E extends Packet> void fireReceiving(WrappedPacket<E> packet);
}
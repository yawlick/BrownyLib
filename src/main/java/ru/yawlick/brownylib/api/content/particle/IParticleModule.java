package ru.yawlick.brownylib.api.content.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;

import java.util.function.Consumer;

public interface IParticleModule {
    void sendParticle(ParticleOptions particleOptions, double x, double y, double z, float xOffset, float yOffset, float zOffset, float speed, int count, double dx, double dz);

    ClientboundLevelParticlesPacket createPacket(ParticleOptions particleOptions, boolean overrideLimiter, boolean alwaysShow, double x, double y, double z, float xOffset, float yOffset, float zOffset, float maxSpeed, int count);
}
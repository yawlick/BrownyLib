package ru.yawlick.brownylib.common.content.particle;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.api.content.particle.IParticleModule;
import ru.yawlick.brownylib.common.content.AbstractModule;

public final class ParticleModule extends AbstractModule implements IParticleModule {
    public ParticleModule() {
        load();
    }

    @Override
    public void load() {}

    public void sendParticle(ParticleOptions particleOptions, double x, double y, double z, float xOffset, float yOffset, float zOffset, float speed, int count, double dx, double dz) {
        ClientboundLevelParticlesPacket packet = createPacket(
                particleOptions,
                true, true,
                x + dx, y, z + dz,
                xOffset, yOffset, zOffset,
                speed,
                count
        );

        for(Player player : Bukkit.getOnlinePlayers()) {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            craftPlayer.getHandle().connection.sendPacket(packet);
        }
    }

    public ClientboundLevelParticlesPacket createPacket(ParticleOptions particleOptions, boolean overrideLimiter, boolean alwaysShow, double x, double y, double z, float xOffset, float yOffset, float zOffset, float maxSpeed, int count) {
        return new ClientboundLevelParticlesPacket(
                particleOptions,
                overrideLimiter, alwaysShow,
                (float) x, (float) y, (float) z,
                xOffset, yOffset, zOffset,
                maxSpeed, count
        );
    }

    @Override
    public void unload() {}
}

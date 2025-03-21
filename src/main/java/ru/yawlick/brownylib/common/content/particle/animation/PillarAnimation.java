package ru.yawlick.brownylib.common.content.particle.animation;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.particle.IParticleModule;
import ru.yawlick.brownylib.common.content.particle.AbstractAnimation;
import ru.yawlick.brownylib.common.content.particle.ParticleModule;

public class PillarAnimation extends AbstractAnimation {
    SimpleParticleType particle = ParticleTypes.ITEM_SLIME;
    int particleCount = 1;
    double threads = 4;
    double radius = 2;

    public PillarAnimation(AnimationData data, SimpleParticleType particle, int particleCount, double threads, double radius, long lifetime) {
        super(data);
        this.particle = particle;
        this.particleCount = particleCount;
        this.threads = threads;
        this.radius = radius;
        this.lifetime = lifetime;
    }

    @Override
    public  <T extends AbstractAnimation> T start() {
        new Thread(() -> {
            try {
                IParticleModule particleModule = BrownyLib.getInstance().getParticleModule();

                double duration = data.getDuration();
                double angleIncrement = Math.PI / (threads / 2);

                for (double i = 0; i <= duration; i++) {
                    for (int j = 0; j < threads; j++) {
                        double currentAngle = j * angleIncrement + (i / 10.0);

                        double dx = Math.sin(currentAngle) * radius;
                        double dz = Math.cos(currentAngle) * radius;

                        double x = data.getX() + dx;
                        double y = data.getY() + i / 20;
                        double z = data.getZ() + dz;
                        if(data.isClearAfter()) {
                            ClientboundLevelParticlesPacket packet = particleModule.createPacket(
                                    particle,
                                    false, true,
                                    x + dx, y, z + dz,
                                    0, 0, 0,
                                    0.1f,
                                    particleCount
                            );
                            particles.put(new Location(data.getWorld(), x, y, z), packet);
                            for(Player player : Bukkit.getOnlinePlayers()) {
                                CraftPlayer craftPlayer = (CraftPlayer) player;
                                craftPlayer.getHandle().connection.sendPacket(packet);
                            }
                        } else {
                            particleModule.sendParticle(
                                    particle,
                                    x, y, z,
                                    0, 0, 0,
                                    0.1f,
                                    particleCount,
                                    dx, dz
                            );
                        }
                    }
                    Thread.sleep((long) (50 / data.getSpeedMultiplier()));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return (T) this;
    }

    @Override
    public <T extends AbstractAnimation> T stop() {
        return (T) this;
    }
}

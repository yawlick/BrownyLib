package ru.yawlick.brownylib.common.content.particle.animation;

import lombok.Getter;
import net.minecraft.core.particles.*;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Location;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.particle.IParticleModule;
import ru.yawlick.brownylib.common.content.particle.AbstractAnimation;

@Getter
public class CandyPillarAnimation extends AbstractAnimation {
    int particleCount = 1;
    double threads = 5;
    double radius = 0.1;

    public CandyPillarAnimation(AnimationData data, int particleCount, double threads, double radius) {
        super(data);
        this.radius = radius;
        this.threads = threads;
        this.particleCount = particleCount;
    }

    @Override
    public <T extends AbstractAnimation> T start() {
        new Thread(() -> {
            try {
                SimpleParticleType particle;
                IParticleModule particleModule = BrownyLib.getInstance().getParticleModule();

                double duration = data.getDuration();
                double angleIncrement = Math.PI / (threads / 2);

                for (double i = 0; i <= duration; i++) {
                    for (int j = 0; j < threads; j++) {
                        particle = ParticleTypes.DRIPPING_LAVA;
                        if(j == 0 || j == 2 || j == 4) {
                            particle = ParticleTypes.DRIPPING_WATER;
                        }
                        double currentAngle = j * angleIncrement + (i / 10.0);
                        double dx = Math.cos(currentAngle) * radius;
                        double dz = Math.sin(currentAngle) * radius;

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
}
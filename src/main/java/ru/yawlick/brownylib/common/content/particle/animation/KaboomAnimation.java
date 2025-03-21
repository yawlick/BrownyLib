package ru.yawlick.brownylib.common.content.particle.animation;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Location;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.particle.IParticleModule;
import ru.yawlick.brownylib.common.content.particle.AbstractAnimation;
import ru.yawlick.brownylib.common.content.particle.ParticleModule;

public class KaboomAnimation extends AbstractAnimation {
    SimpleParticleType particle = ParticleTypes.FIREWORK;
    int particleCount = 1;

    public KaboomAnimation(AnimationData data, SimpleParticleType particle, int particleCount) {
        super(data);
        this.particle = particle;
        this.particleCount = particleCount;
    }

    @Override
    public  <T extends AbstractAnimation> T start() {
        new Thread(() -> {
            try {
                IParticleModule particleModule = BrownyLib.getInstance().getParticleModule();
                double duration = data.getDuration();

                for (double i = 0; i <= duration; i++) {
                    double x = data.getX();
                    double y = data.getY() + i / 20;
                    double z = data.getZ();
                    if(data.isClearAfter()) {
                        ClientboundLevelParticlesPacket packet = particleModule.createPacket(
                                particle,
                                false, true,
                                x, y, z,
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
                                0.0, 0.0
                        );
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

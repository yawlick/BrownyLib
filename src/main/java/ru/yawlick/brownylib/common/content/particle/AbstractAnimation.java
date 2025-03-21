package ru.yawlick.brownylib.common.content.particle;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.api.content.particle.Animation;

import java.util.LinkedHashMap;
import java.util.function.Consumer;

@Getter @Setter
public abstract class AbstractAnimation implements Animation {
    public final AnimationData data;
    public final LinkedHashMap<Location, ClientboundLevelParticlesPacket> particles = new LinkedHashMap<>();
    public Consumer<AnimationData> consumer;
    public long lifetime = 50L;

    public AbstractAnimation(AnimationData data) {
        this.data = data;
        Thread thread = new Thread(() -> {
            try {
                double duration = data.duration + data.afterSeconds * 20;
                if(data.clearAfter) {
                    Thread.sleep(10);
                    for (double i = 0; i <= duration / ((double) lifetime / 50); i++) {
                        sendAllPackets();
                        Thread.sleep(lifetime);
                    }
                    Thread.sleep((long) (data.afterSeconds * 1000));
                    clearParticles();
                } else {
                    if(consumer != null) {
                        Thread.sleep((long) (lifetime * (duration / ((double) lifetime / 50)) + (long) (data.afterSeconds * 1000)));
                        consumer.accept(data);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public <T extends AbstractAnimation> T start() {
        return (T) this;
    }

    public <T extends AbstractAnimation> T stop() {
        clearParticles();
        return (T) this;
    }

    public void sendAllPackets() {
        for(ClientboundLevelParticlesPacket packetToSend : particles.values()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                CraftPlayer craftPlayer = (CraftPlayer) player;
                craftPlayer.getHandle().connection.sendPacket(packetToSend);
            }
        }
    }

    public void clearParticles() {
        particles.clear();
    }

    public <T extends AbstractAnimation> T andAfterDo(Consumer<AnimationData> consumer) {
        this.consumer = consumer;
        return (T) this;
    }

    @Getter
    public static class AnimationData {
        protected Location location;
        protected double duration;
        protected double speedMultiplier;
        protected boolean clearAfter;
        protected double afterSeconds;

        public AnimationData(Location location, double duration, double speedMultiplier, boolean clearAfter, double afterSeconds) {
            this.location = location;
            this.duration = duration;
            this.speedMultiplier = speedMultiplier;
            this.clearAfter = clearAfter;
            this.afterSeconds = afterSeconds;
        }

        public World getWorld() {
            return location.getWorld();
        }

        public double getX() {
            return location.getX();
        }

        public double getY() {
            return location.getY();
        }

        public double getZ() {
            return location.getZ();
        }
    }
}

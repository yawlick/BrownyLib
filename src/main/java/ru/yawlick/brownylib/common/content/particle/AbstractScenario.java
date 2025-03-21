package ru.yawlick.brownylib.common.content.particle;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import ru.yawlick.brownylib.api.content.particle.Animation;
import ru.yawlick.brownylib.api.content.particle.Scenario;

import java.util.ArrayList;
import java.util.function.Consumer;

@Getter @Setter
public abstract class AbstractScenario implements Scenario {
    public final ScenarioData data;
    public final ArrayList<Animation> animations = new ArrayList<>();
    public Consumer<ScenarioData> consumer;

    public AbstractScenario(ScenarioData data) {
        this.data = data;
    }

    public <T extends AbstractScenario> T start() {
        return (T) this;
    }

    public <T extends AbstractScenario> T stop() {
        for(Animation animation : animations) {
            animation.stop();
        }
        return (T) this;
    }

    public <T extends AbstractScenario> T andAfterDo(Consumer<ScenarioData> consumer) {
        this.consumer = consumer;
        return (T) this;
    }

    @Getter
    public static class ScenarioData {
        protected Location location;
        protected double speedMultiplier;

        public ScenarioData(Location location, double speedMultiplier) {
            this.location = location;
            this.speedMultiplier = speedMultiplier;
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

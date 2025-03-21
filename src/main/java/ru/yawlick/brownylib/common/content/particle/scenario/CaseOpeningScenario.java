package ru.yawlick.brownylib.common.content.particle.scenario;

import net.minecraft.core.particles.ParticleTypes;
import org.bukkit.Location;
import ru.yawlick.brownylib.common.content.particle.AbstractAnimation;
import ru.yawlick.brownylib.common.content.particle.animation.PillarAnimation;

import java.util.function.Consumer;

public class CaseOpeningScenario extends AbstractAnimation {
    public CaseOpeningScenario(AnimationData animationData) {
        super(animationData);
    }

    @Override
    public  <T extends AbstractAnimation> T start() {

        // Кабум Рико, кабум
        Consumer<AnimationData> kaboom = (data) -> {
            //new KaboomAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX(), data.getY(), data.getZ()), 75, 1, false, 0), Particles.FIREWORKS_SPARK, 1);
        };

        //new CandyPillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX() + 2, data.getY(), data.getZ()), 140, 1.4, false, 0), 1, 6, 0.1).andAfterDo(kaboom).start();
        //new PillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX() + 2, data.getY(), data.getZ()), 140, 1.4, true, 0), Particles.END_ROD, 1, 1, 0.05, 500L).start();

        new PillarAnimation(new AbstractAnimation.AnimationData(new Location(data.getWorld(), data.getX(), data.getY() + 1, data.getZ()), 200, 3.0, false, 0), ParticleTypes.FIREWORK, 3000, 1, 0.5, 500L).start();

//        new CandyPillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX() + 1, data.getY(), data.getZ() - 2), 120, 1.2, false, 0), 1, 6, 0.1).andAfterDo(kaboom).start();
//        new CandyPillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX() + 1, data.getY(), data.getZ() + 2), 120, 1.2, false, 0), 1, 6, 0.1).andAfterDo(kaboom).start();
//        new CandyPillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX() - 1, data.getY(), data.getZ() - 2), 100, 1, false, 0), 1, 6, 0.1).andAfterDo(kaboom).start();
//        new CandyPillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX() - 1, data.getY(), data.getZ() + 2), 100, 1, false, 0), 1, 6, 0.1).andAfterDo(kaboom).start();

        //new PillarAnimation(new Animation.AnimationData(new SpecLocation(data.getWorld(), data.getX(), data.getY(), data.getZ()), 100, 1.0, false, 2), Particles.DRAGON_BREATH, 1, 8, 2, 0L).start();
        new Thread(() -> {
            try {
                new PillarAnimation(new AbstractAnimation.AnimationData(new Location(data.getWorld(), data.getX(), data.getY(), data.getZ()), 100, 1.0, false, 0), ParticleTypes.BUBBLE, 1, 16, 2, 500L).start();
                Thread.sleep(600);
                new PillarAnimation(new AbstractAnimation.AnimationData(new Location(data.getWorld(), data.getX(), data.getY(), data.getZ()), 100, 1.0, false, 0), ParticleTypes.BUBBLE, 1, 16, 2, 500L).start();
                Thread.sleep(600);
                new PillarAnimation(new AbstractAnimation.AnimationData(new Location(data.getWorld(), data.getX(), data.getY(), data.getZ()), 100, 1.0, false, 0), ParticleTypes.BUBBLE, 1, 16, 2, 500L).start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return (T) this;
    }
}

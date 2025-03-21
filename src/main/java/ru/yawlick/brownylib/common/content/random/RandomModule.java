package ru.yawlick.brownylib.common.content.random;

import lombok.Getter;
import ru.yawlick.brownylib.api.content.random.IRandomModule;
import ru.yawlick.brownylib.common.content.AbstractModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
public final class RandomModule<T> extends AbstractModule implements IRandomModule<T> {
    private final Map<T, Integer> weightMap;
    private final Random random;
    private final int totalWeight;

    public RandomModule() {
        weightMap = null;
        random = null;
        totalWeight = 0;
    }

    public RandomModule(Map<T, Integer> weightMap, Random random, int totalWeight) {
        this.weightMap = weightMap;
        this.random = random;
        this.totalWeight = totalWeight;
    }

    public T get() {
        if(totalWeight == 0)
            return null;
        int randomValue = random.nextInt(totalWeight);
        int currentSum = 0;

        for (Map.Entry<T, Integer> entry : weightMap.entrySet()) {
            currentSum += entry.getValue();
            if (randomValue < currentSum) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Error while trying to random");
    }

    @Override
    public void load() {}

    @Override
    public void unload() {}
}
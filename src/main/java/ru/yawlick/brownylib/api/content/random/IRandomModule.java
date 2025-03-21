package ru.yawlick.brownylib.api.content.random;

import net.minecraft.network.protocol.Packet;
import ru.yawlick.brownylib.common.content.random.RandomModule;

import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public interface IRandomModule<T> {
    Map<T, Integer> getWeightMap();

    Random getRandom();

    int getTotalWeight();

    T get();

    static <R> RandomModule<R> create(Map<R, Integer> weightMap) {
        int sum = 0;
        for (int weight : weightMap.values()) {
            if (weight <= 0) throw new IllegalArgumentException("Random weight will be more then 0");
            sum += weight;
        }
        return new RandomModule<R>(weightMap, new Random(), sum);
    }
}
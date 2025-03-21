package ru.yawlick.brownylib.common.content.world;

import org.bukkit.*;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.api.content.world.IWorldModule;
import ru.yawlick.brownylib.common.content.AbstractModule;
import ru.yawlick.brownylib.common.content.world.generator.EmptyWorldGenerator;
import ru.yawlick.brownylib.common.util.TextUtil;

public final class WorldModule extends AbstractModule implements IWorldModule {
    public WorldModule() {
        load();
    }

    public void createEmptyWorld(String name) {
        if(Bukkit.getWorld(name) == null)
            return;
        WorldCreator creator = new WorldCreator(name);
        creator.generator(new EmptyWorldGenerator());
        World world = creator.createWorld();
        world.setTime(1000L);
        world.setPVP(false);
        world.getWorldBorder().setCenter(0.5, 0.5);
        world.getWorldBorder().setWarningDistance(0);
        world.setDifficulty(Difficulty.EASY);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
    }

    @Override
    public void load() {
        createEmptyWorld("build");
    }

    @Override
    public void unload() {
        for(World world : Bukkit.getWorlds()) {
            for(Player player : world.getPlayers()) {
                player.kick(TextUtil.formatText("#FF0000Сохранение мира..."));
            }
            world.save();
        }
    }
}

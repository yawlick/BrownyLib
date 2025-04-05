package ru.yawlick.brownylib;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.api.content.command.ICommandModule;
import ru.yawlick.brownylib.api.content.data.DataContainer;
import ru.yawlick.brownylib.api.content.data.DataGroup;
import ru.yawlick.brownylib.api.content.data.DataStore;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.api.content.entity.ICustomEntityModule;
import ru.yawlick.brownylib.api.content.event.IEventModule;
import ru.yawlick.brownylib.api.content.menu.IMenuModule;
import ru.yawlick.brownylib.api.content.network.INetworkModule;
import ru.yawlick.brownylib.api.content.particle.IParticleModule;
import ru.yawlick.brownylib.api.content.random.IRandomModule;
import ru.yawlick.brownylib.api.content.scoreboard.IScoreboardModule;
import ru.yawlick.brownylib.api.content.world.IWorldModule;
import ru.yawlick.brownylib.common.config.ConfigManager;
import ru.yawlick.brownylib.common.content.command.CommandModule;
import ru.yawlick.brownylib.common.content.data.DataModule;
import ru.yawlick.brownylib.common.content.entity.CustomEntityModule;
import ru.yawlick.brownylib.common.content.event.EventModule;
import ru.yawlick.brownylib.api.content.menu.Menu;
import ru.yawlick.brownylib.common.content.menu.MenuModule;
import ru.yawlick.brownylib.common.content.network.NetworkModule;
import ru.yawlick.brownylib.common.content.particle.ParticleModule;
import ru.yawlick.brownylib.common.content.random.RandomModule;
import ru.yawlick.brownylib.common.content.scoreboard.ScoreboardModule;
import ru.yawlick.brownylib.common.content.world.WorldModule;

import java.util.HashMap;
import java.util.logging.Logger;

@Getter
public final class BrownyLib extends JavaPlugin implements IFastBrowny {
    public static String PREFIX = " §6[§lBrowny§r§6] » §e";
    public static Logger LOGGER = Logger.getLogger("Browny");
    @Getter private static BrownyLib instance;
    private ConfigManager configManager;

    DataStore brownyDataStore;

    /// -# Modules #-
    private DataModule dataModule;
    private ICommandModule commandModule;
    private ICustomEntityModule customEntityModule;
    private IEventModule eventModule;
    private IMenuModule menuModule;
    private INetworkModule networkModule;
    private IParticleModule particleModule;
    private IRandomModule<String> randomModule;
    private IScoreboardModule scoreboardModule;
    private IWorldModule worldModule;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        configManager.setup(getDataFolder());

        dataModule = new DataModule(); dataModule.load();
        commandModule = new CommandModule();
        customEntityModule = new CustomEntityModule();
        eventModule = new EventModule();
        menuModule = new MenuModule();
        networkModule = new NetworkModule();
        particleModule = new ParticleModule();
        scoreboardModule = new ScoreboardModule();
        worldModule = new WorldModule();

        brownyDataStore = dataModule.getDataStore(this);
        DataGroup group = brownyDataStore.getGroup("player-data");
        DataContainer container = group.getContainer("test-container");
        container.set("key", "value").set("key #2", "value #2").set("lisov", "pidar");

        HashMap<String, Integer> randomTestMap = getTestMap();
        randomModule = IRandomModule.create(randomTestMap);
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
        log(randomModule.get());
    }

    private static @NotNull HashMap<String, Integer> getTestMap() {
        HashMap<String, Integer> randomTestMap = new HashMap<>();
        randomTestMap.put("Этот текст выводится с шансом 50%!", 500);
        randomTestMap.put("Этот текст выводится с шансом 30%!", 300);
        randomTestMap.put("Этот текст выводится с шансом 10%!", 100);
        randomTestMap.put("Этот текст выводится с шансом 5%!", 50);
        randomTestMap.put("Этот текст выводится с шансом 3%!", 30);
        randomTestMap.put("Этот текст выводится с шансом 1.9%!", 19);
        randomTestMap.put("Этот текст выводится с шансом 0.1%!", 1);
        return randomTestMap;
    }

    @Override
    public void onDisable() {
        dataModule.unload();
        worldModule.unload();
    }

    public Menu getMenu(Inventory inventory) {
        Menu inventoryMenu = null;
        for(Menu menu : BrownyLib.getInstance().getMenuModule().getMenuList()) {
            if(menu.getInventory().equals(inventory)) {
                inventoryMenu = menu;
                break;
            }
        }
        return inventoryMenu;
    }

    public CustomEntity getCustomEntity(Entity realEntity) {
        CustomEntity customEntity = null;
        for(CustomEntity entity : BrownyLib.getInstance().getCustomEntityModule().getEntities()) {
            if(entity.getEntity().getEntityId() == realEntity.getEntityId()) {
                customEntity = entity;
                break;
            }
        }
        return customEntity;
    }
}

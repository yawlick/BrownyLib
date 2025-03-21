package ru.yawlick.brownylib.common.content.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.menu.Menu;
import ru.yawlick.brownylib.common.content.menu.event.InventoryMenuEvent;
import ru.yawlick.brownylib.common.util.TextUtil;

import java.util.*;
import java.util.function.Consumer;

@Getter
public abstract class AbstractMenu implements Menu {
    private final Map<Class<? extends InventoryMenuEvent>, Set<Consumer<? extends InventoryMenuEvent>>> registeredEvents = new HashMap<>();
    protected final Inventory inventory;
    protected String id;
    protected String title;

    public AbstractMenu() {
        this(MenuInventorySize._3X9_26);
    }

    public AbstractMenu(MenuInventorySize size) {
        this(size, "§6[ §f§lBrowny §6]");
    }

    public AbstractMenu(MenuInventorySize size, String title) {
        this(size, title, "" + new Random().nextInt());
    }

    public AbstractMenu(MenuInventorySize size, String title, String id) {
        //this.inventory = Bukkit.createInventory(null, size.getSize(), title + " §r§7" + id);
        this.inventory = Bukkit.createInventory(null, size.getSize(), TextUtil.formatText(title));
        this.title = title;
        this.id = id;
        init();
    }

    protected void init() {
        BrownyLib.getInstance().getMenuModule().registerMenu(this);
        log(String.format("Initializing Menu with id %s", this.id));
    }

    public <E extends InventoryMenuEvent> void registerEvent(Class<E> event, Consumer<E> consumer) {
        if(event != null && consumer != null) {
            Set<Consumer<? extends InventoryMenuEvent>> eventsSet = registeredEvents.computeIfAbsent(event, (k) -> {
                return new HashSet();
            });
            eventsSet.add(consumer);
        }
    }

    public <E extends InventoryMenuEvent> void fireEvent(E event) {
        Set<Consumer<? extends InventoryMenuEvent>> eventsSet = registeredEvents.get(event.getClass());
        if (eventsSet != null) {
            Iterator var3 = eventsSet.iterator();

            while(var3.hasNext()) {
                Consumer<E> consumer = (Consumer<E>) var3.next();
                consumer.accept(event);
            }
        }
    }

    @Override
    public void open(Player player) {
        Bukkit.getScheduler().runTask(BrownyLib.getInstance(), () -> {
            player.openInventory(inventory);
        });
    }

    @Override
    public void close(Player player) {
        Bukkit.getScheduler().runTask(BrownyLib.getInstance(), () -> {
            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        });
    }

    @Override
    public void close() {
        inventory.close();
    }
}

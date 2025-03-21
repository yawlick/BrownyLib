package ru.yawlick.brownylib.api.content.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.common.content.menu.event.InventoryMenuEvent;

import java.util.function.Consumer;

public interface Menu extends IFastBrowny {
    <E extends InventoryMenuEvent> void registerEvent(Class<E> event, Consumer<E> consumer);

    <E extends InventoryMenuEvent> void fireEvent(E event);

    Inventory getInventory();

    String getId();

    String getTitle();

    void open(Player player);

    void close(Player player);

    void close();
}

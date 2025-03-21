package ru.yawlick.brownylib.common.content.event.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.event.EventTracker;
import ru.yawlick.brownylib.api.content.menu.Menu;
import ru.yawlick.brownylib.common.content.menu.event.impl.MenuClickEvent;
import ru.yawlick.brownylib.common.util.ItemUtil;

public class InventoryClickEventTracker implements EventTracker {
    @EventHandler(priority = EventPriority.HIGHEST)
    void onInventoryClick(InventoryClickEvent event) {
        Menu menu = BrownyLib.getInstance().getMenu(event.getClickedInventory());
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if(menu == null || item == null) {
            return;
        }
        if(ItemUtil.hasNBT(item, "browny.open-menu", "")) {
            menu.close(player);
            BrownyLib.getInstance().getMenuModule().get(ItemUtil.readNBT(item, "browny.open-menu", "")).open(player);
        }
        MenuClickEvent menuClickEvent = new MenuClickEvent(
                menu,
                player,
                event.getClick(),
                event.getAction(),
                event.getSlot(),
                item
        );
        BrownyLib.getInstance().getMenu(event.getClickedInventory()).fireEvent(menuClickEvent);
        if(menuClickEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}

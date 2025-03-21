package ru.yawlick.brownylib.common.content.menu.event.impl;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import ru.yawlick.brownylib.api.content.menu.Menu;
import ru.yawlick.brownylib.common.content.menu.event.Cancellable;
import ru.yawlick.brownylib.common.content.menu.event.InventoryMenuEvent;

public class MenuClickEvent extends InventoryMenuEvent implements Cancellable {
    @Getter
    private final Player player;
    @Getter
    private final ClickType clickType;
    @Getter
    private final InventoryAction action;
    @Getter
    private final int slot;
    @Getter
    private final ItemStack itemStack;

    public MenuClickEvent(Menu menu, Player player, ClickType clickType, InventoryAction action, int slot, ItemStack itemStack) {
        super(menu);
        this.player = player;
        this.clickType = clickType;
        this.action = action;
        this.slot = slot;
        if(itemStack == null) {
            this.itemStack = new ItemStack(Material.AIR);
        } else {
            this.itemStack = itemStack;
        }
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}

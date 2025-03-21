package ru.yawlick.brownylib.common.content.menu.event;

import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.api.content.menu.Menu;

public class InventoryMenuEvent implements IFastBrowny {
    protected boolean cancelled = false;
    final Menu menu;

    public InventoryMenuEvent(Menu menu) {
        this.menu = menu;
    }
}

package ru.yawlick.brownylib.common.content.menu.event;

import org.bukkit.event.Listener;
import ru.yawlick.brownylib.api.IFastBrowny;

public interface Cancellable extends IFastBrowny, Listener {
    void setCancelled(boolean state);

    boolean isCancelled();
}
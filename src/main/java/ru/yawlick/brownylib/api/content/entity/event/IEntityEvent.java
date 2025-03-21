package ru.yawlick.brownylib.api.content.entity.event;

import org.bukkit.event.Listener;
import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;

public interface IEntityEvent extends IFastBrowny, Listener {
    CustomEntity getEntity();
}
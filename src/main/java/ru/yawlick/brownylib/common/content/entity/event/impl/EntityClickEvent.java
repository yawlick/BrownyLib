package ru.yawlick.brownylib.common.content.entity.event.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.api.content.entity.event.Cancellable;
import ru.yawlick.brownylib.common.content.entity.event.EntityEvent;

public class EntityClickEvent extends EntityEvent implements Cancellable {
    @Getter
    private final Player player;

    public EntityClickEvent(CustomEntity entity, Player player) {
        super(entity);
        this.player = player;
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

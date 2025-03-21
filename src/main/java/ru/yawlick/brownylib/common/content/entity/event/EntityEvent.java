package ru.yawlick.brownylib.common.content.entity.event;

import lombok.Getter;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.api.content.entity.event.IEntityEvent;

public class EntityEvent implements IEntityEvent {
    protected boolean cancelled = false;
    @Getter
    protected final CustomEntity entity;

    public EntityEvent(CustomEntity entity) {
        this.entity = entity;
    }
}

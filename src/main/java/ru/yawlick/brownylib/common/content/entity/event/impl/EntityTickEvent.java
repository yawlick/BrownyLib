package ru.yawlick.brownylib.common.content.entity.event.impl;

import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.common.content.entity.event.EntityEvent;

public class EntityTickEvent extends EntityEvent {
    public EntityTickEvent(CustomEntity entity) {
        super(entity);
    }
}

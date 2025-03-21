package ru.yawlick.brownylib.api.content.entity;

import org.bukkit.craftbukkit.entity.CraftEntity;
import ru.yawlick.brownylib.api.IFastBrowny;
import ru.yawlick.brownylib.common.content.entity.event.EntityEvent;

import java.util.function.Consumer;

public interface CustomEntity extends IFastBrowny {
    CraftEntity getEntity();

    <E extends EntityEvent> void registerEvent(Class<E> event, Consumer<E> consumer);

    <E extends EntityEvent> void fireEvent(E event);

    void kill();
}

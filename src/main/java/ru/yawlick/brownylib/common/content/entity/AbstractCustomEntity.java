package ru.yawlick.brownylib.common.content.entity;

import lombok.Getter;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import ru.yawlick.brownylib.BrownyLib;
import ru.yawlick.brownylib.api.content.entity.CustomEntity;
import ru.yawlick.brownylib.common.content.entity.event.EntityEvent;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractCustomEntity implements CustomEntity {
    private final Map<Class<? extends EntityEvent>, Set<Consumer<? extends EntityEvent>>> registeredEvents = new HashMap<>();
    @Getter protected final CraftEntity entity;

    public AbstractCustomEntity(Entity entity) {
        this((CraftEntity) entity);
    }

    public AbstractCustomEntity(CraftEntity entity) {
        this.entity = entity;
        BrownyLib.getInstance().getCustomEntityModule().addEntity(this);
    }

    public <E extends EntityEvent> void registerEvent(Class<E> event, Consumer<E> consumer) {
        if(event != null && consumer != null) {
            Set<Consumer<? extends EntityEvent>> eventsSet = registeredEvents.computeIfAbsent(event, (k) -> {
                return new HashSet();
            });
            eventsSet.add(consumer);
        }
    }

    public <E extends EntityEvent> void fireEvent(E event) {
        Set<Consumer<? extends EntityEvent>> eventsSet = registeredEvents.get(event.getClass());
        if (eventsSet != null) {
            Iterator var3 = eventsSet.iterator();

            while(var3.hasNext()) {
                Consumer<E> consumer = (Consumer<E>) var3.next();
                consumer.accept(event);
            }
        }
    }

    public void kill() {
        entity.remove();
        BrownyLib.getInstance().getCustomEntityModule().removeEntity(this);
    }
}

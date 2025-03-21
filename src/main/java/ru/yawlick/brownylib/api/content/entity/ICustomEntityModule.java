package ru.yawlick.brownylib.api.content.entity;

import java.util.ArrayList;

public interface ICustomEntityModule {
    ArrayList<CustomEntity> getEntities();

    CustomEntity get(int entityId);

    void addEntity(CustomEntity entity);

    void removeEntity(CustomEntity entity);
}

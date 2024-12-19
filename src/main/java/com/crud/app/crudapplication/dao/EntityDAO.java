package com.crud.app.crudapplication.dao;

import com.crud.app.crudapplication.model.Entity;

import java.util.List;
import java.util.UUID;

public interface EntityDAO  {

    List<Entity> getAllEntities();
    Entity getEntityById(UUID id);
    void addEntity(Entity entity);
    void updateEntity(Entity entity);
    void deleteEntity(UUID id);

}

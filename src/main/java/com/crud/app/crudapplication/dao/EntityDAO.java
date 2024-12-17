package com.crud.app.crudapplication.dao;

import com.crud.app.crudapplication.model.Entity;

import java.util.List;

public interface EntityDAO  {

    List<Entity> getAllEntities();
    Entity getEntityById(int id);
    void addEntity(Entity entity);
    void updateEntity(Entity entity);
    void deleteEntity(int id);

}

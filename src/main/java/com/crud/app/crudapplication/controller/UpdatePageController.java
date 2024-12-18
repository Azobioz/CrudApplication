package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.model.Entity;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.Data;

@Data
public class UpdatePageController {

    private Entity selectedEntity;

    @FXML
    private TableColumn<Entity, Integer> idColumn;
    @FXML
    private TableColumn<Entity, String> nameColumn;
    @FXML
    private TableColumn<Entity, String> descriptionColumn;

//    @FXML
//    private void addEntity() {
//        // Создаем новую сущность
//        Entity entity = new Entity();
//        entity.setName(nameColumn.getText()); // Используем getText() для получения значения
//        entity.setDescription(descriptionField.getText()); // Используем getText() для получения значения
//        entity.setId(entityList.size() + 2);
//        // Добавляем сущность в базу данных
//        entityDAO.addEntity(entity);
//
//        // Добавляем сущность в список
//        entityList.add(entity);
//
//        // Очищаем поля ввода
//        nameField.clear();
//        descriptionField.clear();
//    }
}

package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.CrudApplication;
import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.dao.daoimpl.EntityDAOImpl;
import com.crud.app.crudapplication.model.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainPageController {
    @FXML
    private TableView<Entity> entityTable;
    @FXML
    private TableColumn<Entity, Integer> idColumn;
    @FXML
    private TableColumn<Entity, String> nameColumn;
    @FXML
    private TableColumn<Entity, String> descriptionColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;

    private EntityDAO entityDAO;
    private ObservableList<Entity> entityList;

    public MainPageController() {
        entityDAO = new EntityDAOImpl();
        entityList = FXCollections.observableArrayList(entityDAO.getAllEntities());
    }

    @FXML
    private void initialize() {
        // Устанавливаем фабрики для столбцов
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Устанавливаем данные в таблицу
        entityTable.setItems(entityList);
    }

    @FXML
    public void handleAddButton() {
        Entity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        if (selectedEntity != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(CrudApplication.class.getResource("/AddPage.fxml"));
                Parent secondPane = fxmlLoader.load();
                // Получаем контроллер второй панели
                AddPageController secondController = fxmlLoader.getController();

                // Передаем объект в контроллер
                secondController.setSelectedEntity(selectedEntity);

                // Устанавливаем новую панель в сцену

                Scene scene = new Scene(secondPane);
                Stage stage = new Stage();
                stage.setTitle("Add Entity");
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @FXML
    private void addEntity() {
        // Создаем новую сущность
        Entity entity = new Entity();
        entity.setName(nameField.getText()); // Используем getText() для получения значения
        entity.setDescription(descriptionField.getText()); // Используем getText() для получения значения
        entity.setId(entityList.size() + 2);
        // Добавляем сущность в базу данных
        entityDAO.addEntity(entity);

        // Добавляем сущность в список
        entityList.add(entity);

        // Очищаем поля ввода
        nameField.clear();
        descriptionField.clear();
    }

    @FXML
    private void updateEntity() {
        // Получаем выбранную сущность
        Entity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        if (selectedEntity != null) {
            // Обновляем поля сущности
            selectedEntity.setName(nameField.getText()); // Используем getText() для получения значения
            selectedEntity.setDescription(descriptionField.getText()); // Используем getText() для получения значения

            // Обновляем сущность в базе данных
            entityDAO.updateEntity(selectedEntity);

            // Обновляем таблицу
            entityTable.refresh();

            // Очищаем поля ввода
            nameField.clear();
            descriptionField.clear();
        }
    }

    @FXML
    private void deleteEntity() {
        // Получаем выбранную сущность
        Entity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        if (selectedEntity != null) {
            // Удаляем сущность из базы данных
            entityDAO.deleteEntity(selectedEntity.getId()); // Используем простое поле id

            // Удаляем сущность из списка
            entityList.remove(selectedEntity);
        }
    }
}
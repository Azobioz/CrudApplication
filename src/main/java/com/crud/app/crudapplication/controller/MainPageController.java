package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.CrudApplication;
import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.dao.daoimpl.EntityDAOImpl;
import com.crud.app.crudapplication.model.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

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
    private TableColumn<Entity, String> createdAtColumn;
    @FXML
    private TableColumn<Entity, String> updatedAtColumn;
    @FXML
    private TextField searchTextField;

    private EntityDAO entityDAO;
    private ObservableList<Entity> entityList;
    private FilteredList<Entity> filteredEntityList;

    public MainPageController() {
        entityDAO = new EntityDAOImpl();
        entityList = FXCollections.observableArrayList(entityDAO.getAllEntities());

    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        entityTable.setItems(entityList);
        filteredEntityList = new FilteredList<>(entityList, p -> true);

        entityTable.setItems(filteredEntityList);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEntityList.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (entity.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (entity.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    @FXML
    public void updateButton() {
        Entity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        if (selectedEntity != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(CrudApplication.class.getResource("/UpdatePage.fxml"));
                Parent secondPane = fxmlLoader.load();
                UpdatePageController updatePageController = fxmlLoader.getController();
                updatePageController.setSelectedEntity(selectedEntity);
                updatePageController.getEntityName().setText(selectedEntity.getName());
                updatePageController.getEntityDescription().setText(selectedEntity.getDescription());

                Scene scene = new Scene(secondPane);
                Stage stage = new Stage();
                stage.setTitle("Add Entity");
                stage.setScene(scene);
                stage.showAndWait();
                refresh();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void addButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CrudApplication.class.getResource("/AddPage.fxml"));
            Parent secondPane = fxmlLoader.load();

            Scene scene = new Scene(secondPane);
            Stage stage = new Stage();
            stage.setTitle("Add Entity");
            stage.setScene(scene);
            stage.showAndWait();
            refresh();

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void deleteButton() {
        Entity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        try {
            if (selectedEntity != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(CrudApplication.class.getResource("/ConfirmToDeletePage.fxml"));

                Parent secondPane = fxmlLoader.load();
                ConfirmToDeleteController confirmToDeleteController = fxmlLoader.getController();
                Scene scene = new Scene(secondPane);
                Stage stage = new Stage();
                stage.setTitle("Confirm to delete entity");
                stage.setScene(scene);
                stage.showAndWait();

                if (confirmToDeleteController.isConfirmed()) {
                    entityDAO.deleteEntity(selectedEntity.getId());
                    entityList.remove(selectedEntity);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        entityList.removeAll(entityList);
        entityList.addAll(entityDAO.getAllEntities());
    }

}
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

    private EntityDAO entityDAO;
    private ObservableList<Entity> entityList;

    public MainPageController() {
        entityDAO = new EntityDAOImpl();
        entityList = FXCollections.observableArrayList(entityDAO.getAllEntities());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        entityTable.setItems(entityList);
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
                stage.show();
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
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    @FXML
    private void deleteEntity() {
        Entity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        if (selectedEntity != null) {
            entityDAO.deleteEntity(selectedEntity.getId());
            entityList.remove(selectedEntity);
        }
    }
}
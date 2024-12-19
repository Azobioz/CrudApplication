package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.dao.daoimpl.EntityDAOImpl;
import com.crud.app.crudapplication.model.Entity;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddPageController {

    @FXML
    private Button closeButton;
    @FXML
    private Label errorText;
    @FXML
    private TextField entityName;
    @FXML
    private TextField entityDescription;

    private EntityDAO entityDAO;
    private List<Entity> entityList;

    public AddPageController() {
        entityDAO = new EntityDAOImpl();
        entityList = FXCollections.observableArrayList(entityDAO.getAllEntities());
    }

    @FXML
    public void addEntity() {
        Entity entity = new Entity();
        if (entityName.getText().length() < 3) {
            errorText.setVisible(true);
        }
        else {
            entity.setId(UUID.randomUUID());
            entity.setName(entityName.getText());
            entity.setDescription(entityDescription.getText());
            entityDAO.addEntity(entity);
            entityList.add(entity);
            entityName.clear();
            entityDescription.clear();
            errorText.setVisible(false);

        }

    }

    @FXML
    public void cancel () {
        try {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


}

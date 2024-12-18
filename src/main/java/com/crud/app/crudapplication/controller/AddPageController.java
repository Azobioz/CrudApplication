package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.CrudApplication;
import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.dao.daoimpl.EntityDAOImpl;
import com.crud.app.crudapplication.model.Entity;
import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Data
public class AddPageController {

    @FXML
    private Button closeButton;
    @FXML
    private Label errorText;

    private int idColumn;
    @FXML
    private TextField nameColumn;
    @FXML
    private TextField descriptionColumn;

    private EntityDAO entityDAO;
    private List<Entity> entityList;

    public AddPageController() {
        entityDAO = new EntityDAOImpl();
        entityList = FXCollections.observableArrayList(entityDAO.getAllEntities());
    }

    @FXML
    public void addEntity() {
        Entity entity = new Entity();
        if (nameColumn.getText().length() < 3) {
            errorText.setVisible(true);
        }
        else {
            entity.setName(nameColumn.getText());
            entity.setDescription(descriptionColumn.getText());
            entity.setId(entityList.size() + 2);
            entityDAO.addEntity(entity);
            entityList.add(entity);
            nameColumn.setText("");
            descriptionColumn.setText("");
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

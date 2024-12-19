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

@Data
public class UpdatePageController {

    @FXML
    private Label successText;
    private Entity selectedEntity;
    @FXML
    private Button closeButton;
    @FXML
    private TextField entityName;
    @FXML
    private TextField entityDescription;
    private EntityDAO entityDAO;

    public UpdatePageController() {
        entityDAO = new EntityDAOImpl();
    }

   @FXML
    public void updateEntity() {
        if (selectedEntity != null) {
            selectedEntity.setName(entityName.getText());
            selectedEntity.setDescription(entityDescription.getText());
            entityDAO.updateEntity(selectedEntity);
            successText.setVisible(true);
        }
    }

    @FXML
    public void cancel () {
        try {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

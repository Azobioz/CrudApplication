package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.dao.daoimpl.EntityDAOImpl;
import com.crud.app.crudapplication.model.Entity;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePageController {

    @FXML
    public Label successText;
    private Entity selectedEntity;
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
}

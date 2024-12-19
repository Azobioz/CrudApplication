package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.model.Entity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class ConfirmToDeleteController {

    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    private boolean isConfirmed;

    @FXML
    public void initialize() {
        yesButton.setOnAction(event -> {
            isConfirmed = true;
            closeDialog();
        });

        noButton.setOnAction(event -> {
            isConfirmed = false;
            closeDialog();
        });
    }


    private void closeDialog() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }
}

module com.crud.app.crudapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires mysql.connector.j;


    exports com.crud.app.crudapplication.controller;
    opens com.crud.app.crudapplication.controller to javafx.fxml;

    exports com.crud.app.crudapplication;

    opens com.crud.app.crudapplication.model to javafx.base;
}
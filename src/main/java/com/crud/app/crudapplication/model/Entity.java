package com.crud.app.crudapplication.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
public class Entity {

    private int id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}

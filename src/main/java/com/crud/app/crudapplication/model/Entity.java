package com.crud.app.crudapplication.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class Entity {

    private UUID id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}

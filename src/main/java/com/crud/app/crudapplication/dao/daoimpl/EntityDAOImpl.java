package com.crud.app.crudapplication.dao.daoimpl;

import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.model.Entity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityDAOImpl implements EntityDAO {

    private String url = "jdbc:mysql://localhost:3306";
    private String username = "admin";
    private String password = "12345";

    public EntityDAOImpl() {
        try {
            createDatabaseIfNotExists();

            useDatabase();

            createTableIfNotExists();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabaseIfNotExists() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            String createDbSQL = "CREATE DATABASE IF NOT EXISTS crudapplication_db";
            statement.executeUpdate(createDbSQL);
        }
    }

    private void useDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            String useDbSQL = "USE crudapplication_db";
            statement.executeUpdate(useDbSQL);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS entity (" +
                    "id CHAR(36) PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "description VARCHAR(255), " +
                    "createdAt TIMESTAMP, " +
                    "updatedAt TIMESTAMP" +
                    ")";
            statement.executeUpdate(createTableSQL);
        }
    }

    @Override
    public List<Entity> getAllEntities() {
        List<Entity> listOfEntities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url + "/crudapplication_db", username, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM entity")) {

            while (rs.next()) {
                Entity entity = new Entity();
                entity.setId(UUID.fromString(rs.getString("id")));
                entity.setName(rs.getString("name"));
                entity.setDescription(rs.getString("description"));
                entity.setCreatedAt(rs.getTimestamp("createdAt"));
                entity.setUpdatedAt(rs.getTimestamp("updatedAt"));
                listOfEntities.add(entity);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfEntities;
    }

    @Override
    public Entity getEntityById(UUID id) {
        String query = "SELECT * FROM entity WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url + "/crudapplication_db", username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Entity entity = new Entity();
                entity.setId(UUID.fromString(rs.getString("id")));
                entity.setName(rs.getString("name"));
                entity.setDescription(rs.getString("description"));
                entity.setCreatedAt(rs.getTimestamp("createdAt"));
                entity.setUpdatedAt(rs.getTimestamp("updatedAt"));
                return entity;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addEntity(Entity entity) {
        String query = "INSERT INTO entity (id, name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url + "/crudapplication_db", username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getId().toString());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntity(Entity entity) {
        String query = "UPDATE entity SET name = ?, description = ?, updatedAt = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url + "/crudapplication_db", username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.setString(4, entity.getId().toString());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntity(UUID id) {
        String query = "DELETE FROM entity WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url + "/crudapplication_db", username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
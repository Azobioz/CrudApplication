package com.crud.app.crudapplication.dao.daoimpl;

import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.model.Entity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntityDAOImpl implements EntityDAO {

    private String url = "jdbc:mysql://localhost:3306/crudapplication_db";
    private String username = "admin";
    private String password = "12345";

    @Override
    public List<Entity> getAllEntities() {
        List<Entity> listOfEntities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM entity")) {

            while (rs.next()) {
                Entity entity = new Entity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setDescription(rs.getString("description"));
                entity.setCreatedAt(rs.getTimestamp("createdAt"));
                entity.setUpdatedAt(rs.getTimestamp("updatedAt"));
                listOfEntities.add(entity);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return listOfEntities;
    }

    @Override
    public Entity getEntityById(int id) {
        String query = "SELECT * FROM entity WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Entity entity = new Entity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setDescription(rs.getString("description"));
                entity.setCreatedAt(rs.getTimestamp("createdAt"));
                entity.setUpdatedAt(rs.getTimestamp("updatedAt"));
                return entity;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void addEntity(Entity entity) {
        String query = "INSERT INTO entity (name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getName().toString());
            preparedStatement.setString(2, entity.getDescription().toString());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(Entity entity) {
        String query = "UPDATE entity SET name = ?, description = ?, updatedAt = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getName().toString());
            preparedStatement.setString(2, entity.getDescription().toString());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, entity.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Entity updated successfully!");
            } else {
                System.out.println("No entity found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        String query = "DELETE FROM entity WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Entity deleted successfully!");
            } else {
                System.out.println("No entity found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
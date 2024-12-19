package com.crud.app.crudapplication.controller;

import com.crud.app.crudapplication.dao.EntityDAO;
import com.crud.app.crudapplication.dao.daoimpl.EntityDAOImpl;
import com.crud.app.crudapplication.model.Entity;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EntityDAOImplTest {

    private static EntityDAO entityDAO;
    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/crudapplication_db";
        String username = "admin";
        String password = "12345";
        connection = DriverManager.getConnection(url, username, password);
        entityDAO = new EntityDAOImpl();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void createTestData() throws SQLException {
        String insertQuery = "INSERT INTO entity (id, name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            UUID id = UUID.randomUUID();
            preparedStatement.setString(1, id.toString());
            preparedStatement.setString(2, "Test Entity");
            preparedStatement.setString(3, "Test Description");
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        }
    }

    @AfterEach
    public void cleanTestData() throws SQLException {
        String deleteQuery = "DELETE FROM entity WHERE name = 'Test Entity'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testGetAllEntities() {
        List<Entity> entities = entityDAO.getAllEntities();
        assertFalse(entities.isEmpty());
    }

    @Test
    public void testGetEntityById() {
        UUID id = UUID.randomUUID();
        Entity entity = new Entity();
        entity.setId(id);
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        entityDAO.addEntity(entity);

        Entity retrievedEntity = entityDAO.getEntityById(id);
        assertNotNull(retrievedEntity);
        assertEquals("Test Entity", retrievedEntity.getName());
    }

    @Test
    public void testAddEntity() {
        UUID id = UUID.randomUUID();
        Entity entity = new Entity();
        entity.setId(id);
        entity.setName("New Entity");
        entity.setDescription("New Description");
        entityDAO.addEntity(entity);

        Entity retrievedEntity = entityDAO.getEntityById(id);
        assertNotNull(retrievedEntity);
        assertEquals("New Entity", retrievedEntity.getName());
    }

    @Test
    public void testUpdateEntity() {
        UUID id = UUID.randomUUID();
        Entity entity = new Entity();
        entity.setId(id);
        entity.setName("Old Entity");
        entity.setDescription("Old Description");
        entityDAO.addEntity(entity);

        entity.setName("Updated Entity");
        entity.setDescription("Updated Description");
        entityDAO.updateEntity(entity);

        Entity updatedEntity = entityDAO.getEntityById(id);
        assertNotNull(updatedEntity);
        assertEquals("Updated Entity", updatedEntity.getName());
    }

    @Test
    public void testDeleteEntity() {
        UUID id = UUID.randomUUID();
        Entity entity = new Entity();
        entity.setId(id);
        entity.setName("Delete Entity");
        entity.setDescription("Delete Description");
        entityDAO.addEntity(entity);

        entityDAO.deleteEntity(id);

        Entity deletedEntity = entityDAO.getEntityById(id);
        assertNull(deletedEntity);
    }
}
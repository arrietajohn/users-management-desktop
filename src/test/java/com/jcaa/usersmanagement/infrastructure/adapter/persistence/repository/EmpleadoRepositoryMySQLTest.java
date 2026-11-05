/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoRepositoryMySQLTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private EmpleadoRepositoryMySQL repository;

    private EmpleadoModel empleado;

    @BeforeEach
    void setUp() {
        empleado = new EmpleadoModel(
                "EMP-001",
                "Aldair",
                "123456789",
                "Auxiliar",
                new BigDecimal("1500000")
        );
    }

    @Test
    void saveShouldReturnEmpleado() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        EmpleadoModel resultado = repository.save(empleado);

        assertEquals(empleado, resultado);
        verify(statement).executeUpdate();
    }

    @Test
    void updateShouldReturnEmpleado() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        EmpleadoModel resultado = repository.update(empleado);

        assertEquals(empleado, resultado);
        verify(statement).executeUpdate();
    }

    @Test
    void getByIdShouldReturnEmpleado() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        prepararResultado();

        Optional<EmpleadoModel> resultado = repository.getById("EMP-001");

        assertTrue(resultado.isPresent());
        assertEquals("Aldair", resultado.get().getNombre());
    }

    @Test
    void getByDocumentoShouldReturnEmpleado() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        prepararResultado();

        Optional<EmpleadoModel> resultado =
                repository.getByDocumento("123456789");

        assertTrue(resultado.isPresent());
        assertEquals("EMP-001", resultado.get().getId());
    }

    @Test
    void getAllShouldReturnEmpleados() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        prepararResultado();

        var resultado = repository.getAll();

        assertEquals(1, resultado.size());
        assertEquals("Aldair", resultado.get(0).getNombre());
    }

    @Test
    void deleteShouldExecuteUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.delete("EMP-001");

        verify(statement).executeUpdate();
    }

    private void prepararResultado() throws SQLException {
        when(resultSet.getString("id")).thenReturn("EMP-001");
        when(resultSet.getString("nombre")).thenReturn("Aldair");
        when(resultSet.getString("documento")).thenReturn("123456789");
        when(resultSet.getString("cargo")).thenReturn("Auxiliar");
        when(resultSet.getBigDecimal("salario"))
                .thenReturn(new BigDecimal("1500000"));
    }
}

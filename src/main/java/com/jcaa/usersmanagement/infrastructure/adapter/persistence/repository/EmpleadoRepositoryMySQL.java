/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;
import com.jcaa.usersmanagement.application.port.out.DeleteEmpleadoPort;
import com.jcaa.usersmanagement.application.port.out.GetAllEmpleadosPort;
import com.jcaa.usersmanagement.application.port.out.GetEmpleadoByDocumentoPort;
import com.jcaa.usersmanagement.application.port.out.GetEmpleadoByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveEmpleadoPort;
import com.jcaa.usersmanagement.application.port.out.UpdateEmpleadoPort;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EmpleadoPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EmpleadoEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.EmpleadoPersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.EmpleadoPersistenceMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class EmpleadoRepositoryMySQL
        implements SaveEmpleadoPort,
        UpdateEmpleadoPort,
        GetEmpleadoByIdPort,
        GetEmpleadoByDocumentoPort,
        GetAllEmpleadosPort,
        DeleteEmpleadoPort {

    private static final String SQL_INSERT =
            "INSERT INTO empleados (id, nombre, documento, cargo, salario) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE empleados SET nombre = ?, documento = ?, cargo = ?, salario = ? "
            + "WHERE id = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id, nombre, documento, cargo, salario "
            + "FROM empleados WHERE id = ? LIMIT 1";

    private static final String SQL_SELECT_BY_DOCUMENTO =
            "SELECT id, nombre, documento, cargo, salario "
            + "FROM empleados WHERE documento = ? LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT id, nombre, documento, cargo, salario "
            + "FROM empleados ORDER BY nombre ASC";

    private static final String SQL_DELETE =
            "DELETE FROM empleados WHERE id = ?";

    private final Connection connection;

    @Override
    public EmpleadoModel save(final EmpleadoModel empleado) {
        final EmpleadoPersistenceDto dto =
                EmpleadoPersistenceMapper.fromModelToDto(empleado);

        try (PreparedStatement statement =
                connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, dto.id());
            statement.setString(2, dto.nombre());
            statement.setString(3, dto.documento());
            statement.setString(4, dto.cargo());
            statement.setBigDecimal(5, dto.salario());

            statement.executeUpdate();
            return empleado;

        } catch (SQLException exception) {
            throw EmpleadoPersistenceException.becauseSaveFailed(
                    dto.id(), exception);
        }
    }

    @Override
    public EmpleadoModel update(final EmpleadoModel empleado) {
        final EmpleadoPersistenceDto dto =
                EmpleadoPersistenceMapper.fromModelToDto(empleado);

        try (PreparedStatement statement =
                connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, dto.nombre());
            statement.setString(2, dto.documento());
            statement.setString(3, dto.cargo());
            statement.setBigDecimal(4, dto.salario());
            statement.setString(5, dto.id());

            statement.executeUpdate();
            return empleado;

        } catch (SQLException exception) {
            throw EmpleadoPersistenceException.becauseUpdateFailed(
                    dto.id(), exception);
        }
    }

    @Override
    public Optional<EmpleadoModel> getById(final String id) {
        try (PreparedStatement statement =
                connection.prepareStatement(SQL_SELECT_BY_ID)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(mapResultSet(resultSet));
            }

        } catch (SQLException exception) {
            throw EmpleadoPersistenceException.becauseFindFailed(
                    id, exception);
        }
    }

    @Override
    public Optional<EmpleadoModel> getByDocumento(final String documento) {
        try (PreparedStatement statement =
                connection.prepareStatement(SQL_SELECT_BY_DOCUMENTO)) {

            statement.setString(1, documento);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(mapResultSet(resultSet));
            }

        } catch (SQLException exception) {
            throw EmpleadoPersistenceException.becauseFindFailed(
                    documento, exception);
        }
    }

    @Override
    public List<EmpleadoModel> getAll() {
        List<EmpleadoModel> empleados = new ArrayList<>();

        try (PreparedStatement statement =
                connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                empleados.add(mapResultSet(resultSet));
            }

            return empleados;

        } catch (SQLException exception) {
            throw EmpleadoPersistenceException.becauseFindAllFailed(
                    exception);
        }
    }

    @Override
    public void delete(final String id) {
        try (PreparedStatement statement =
                connection.prepareStatement(SQL_DELETE)) {

            statement.setString(1, id);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw EmpleadoPersistenceException.becauseDeleteFailed(
                    id, exception);
        }
    }

    private EmpleadoModel mapResultSet(final ResultSet resultSet)
            throws SQLException {

        EmpleadoEntity entity = new EmpleadoEntity(
                resultSet.getString("id"),
                resultSet.getString("nombre"),
                resultSet.getString("documento"),
                resultSet.getString("cargo"),
                resultSet.getBigDecimal("salario")
        );

        return EmpleadoPersistenceMapper.fromEntityToModel(entity);
    }
}

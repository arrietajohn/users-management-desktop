package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteChildPort;
import com.jcaa.usersmanagement.application.port.out.GetAllChildrenPort;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveChildPort;
import com.jcaa.usersmanagement.application.port.out.UpdateChildPort;
import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ChildPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.ChildPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public final class ChildRepositoryMySQL
    implements SaveChildPort,
        UpdateChildPort,
        GetChildByIdPort,
        GetAllChildrenPort,
        DeleteChildPort {

  private static final String SQL_INSERT =
      "INSERT INTO nino "
          + "(matricula, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, "
          + "fecha_nacimiento, fecha_ingreso, fecha_baja, direccion, ci_responsable, "
          + "relacion_responsable) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE nino SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?, "
          + "segundo_apellido = ?, fecha_nacimiento = ?, fecha_ingreso = ?, fecha_baja = ?, "
          + "direccion = ?, ci_responsable = ?, relacion_responsable = ? "
          + "WHERE matricula = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT matricula, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, "
          + "fecha_nacimiento, fecha_ingreso, fecha_baja, direccion, ci_responsable, "
          + "relacion_responsable "
          + "FROM nino "
          + "WHERE matricula = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT matricula, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, "
          + "fecha_nacimiento, fecha_ingreso, fecha_baja, direccion, ci_responsable, "
          + "relacion_responsable "
          + "FROM nino "
          + "ORDER BY primer_nombre ASC, primer_apellido ASC";

  private static final String SQL_DELETE = "DELETE FROM nino WHERE matricula = ?";

  private final Connection connection;

  @Override
  public ChildModel save(final ChildModel child) {
    final ChildPersistenceDto dto = ChildPersistenceMapper.fromModelToDto(child);
    executeSave(dto);
    return findByIdOrFail(child.getId());
  }

  @Override
  public ChildModel update(final ChildModel child) {
    final ChildPersistenceDto dto = ChildPersistenceMapper.fromModelToDto(child);
    executeUpdate(dto);
    return findByIdOrFail(child.getId());
  }

  @Override
  public Optional<ChildModel> getById(final ChildId childId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, childId.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(ChildPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindChildByIdFailed(childId.value(), exception);
    }
  }

  @Override
  public List<ChildModel> getAll() {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return ChildPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllChildrenFailed(exception);
    }
  }

  @Override
  public void delete(final ChildId childId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, childId.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteChildFailed(childId.value(), exception);
    }
  }

  private void executeSave(final ChildPersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.firstName());
      statement.setString(3, dto.secondName());
      statement.setString(4, dto.firstSurname());
      statement.setString(5, dto.secondSurname());
      statement.setString(6, dto.birthDate());
      statement.setString(7, dto.admissionDate());
      statement.setString(8, dto.dischargeDate());
      statement.setString(9, dto.address());
      statement.setString(10, dto.guardianId());
      statement.setString(11, dto.guardianRelationship());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveChildFailed(dto.id(), exception);
    }
  }

  private void executeUpdate(final ChildPersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.firstName());
      statement.setString(2, dto.secondName());
      statement.setString(3, dto.firstSurname());
      statement.setString(4, dto.secondSurname());
      statement.setString(5, dto.birthDate());
      statement.setString(6, dto.admissionDate());
      statement.setString(7, dto.dischargeDate());
      statement.setString(8, dto.address());
      statement.setString(9, dto.guardianId());
      statement.setString(10, dto.guardianRelationship());
      statement.setString(11, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateChildFailed(dto.id(), exception);
    }
  }

  private ChildModel findByIdOrFail(final ChildId childId) {
    return getById(childId)
        .orElseThrow(() -> ChildNotFoundException.becauseIdWasNotFound(childId.value()));
  }
}

package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeletePersonaPort;
import com.jcaa.usersmanagement.application.port.out.GetAllPersonasPort;
import com.jcaa.usersmanagement.application.port.out.GetPersonaByIdPort;
import com.jcaa.usersmanagement.application.port.out.SavePersonaPort;
import com.jcaa.usersmanagement.application.port.out.UpdatePersonaPort;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.PersonaPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersonaPersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.PersonaPersistenceMapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class PersonaRepositoryMySQL
    implements SavePersonaPort,
        UpdatePersonaPort,
        GetPersonaByIdPort,
        GetAllPersonasPort,
        DeletePersonaPort {

  private static final String INSERT_SQL =
      "INSERT INTO persona (id_persona, nombres, apellidos, fecha_nacimiento, "
          + "id_municipio_nacimiento, edad, estatura, sexo, nivel_estudios, "
          + "situacion_militar, dni, estado_civil, ocupacion, grupo_etnico) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String UPDATE_SQL =
      "UPDATE persona SET nombres = ?, apellidos = ?, fecha_nacimiento = ?, "
          + "id_municipio_nacimiento = ?, edad = ?, estatura = ?, sexo = ?, "
          + "nivel_estudios = ?, situacion_militar = ?, dni = ?, estado_civil = ?, "
          + "ocupacion = ?, grupo_etnico = ? WHERE id_persona = ?";

  private static final String FIND_BY_ID_SQL =
      "SELECT * FROM persona WHERE id_persona = ?";

  private static final String FIND_ALL_SQL =
      "SELECT * FROM persona ORDER BY id_persona ASC";

  private static final String DELETE_SQL =
      "DELETE FROM persona WHERE id_persona = ?";

  private final Connection connection;

  public PersonaRepositoryMySQL(final Connection connection) {
    this.connection = connection;
  }

  @Override
  public PersonaModel save(final PersonaModel persona) {
    final PersonaPersistenceDto dto = PersonaPersistenceMapper.fromModelToDto(persona);

    try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
      statement.setInt(1, dto.idPersona());
      bindFields(statement, dto, 2);
      statement.executeUpdate();
      return persona;
    } catch (SQLException exception) {
      throw new PersonaPersistenceException("guardar", exception);
    }
  }

  @Override
  public PersonaModel update(final PersonaModel persona) {
    final PersonaPersistenceDto dto = PersonaPersistenceMapper.fromModelToDto(persona);

    try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
      bindFields(statement, dto, 1);
      statement.setInt(14, dto.idPersona());

      if (statement.executeUpdate() == 0) {
        throw new PersonaPersistenceException(
            "actualizar: no se encontró el registro", null);
      }

      return persona;
    } catch (SQLException exception) {
      throw new PersonaPersistenceException("actualizar", exception);
    }
  }

  @Override
  public Optional<PersonaModel> getById(final PersonaId personaId) {
    try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
      statement.setInt(1, personaId.value());

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(PersonaPersistenceMapper.fromResultSetToModel(resultSet));
        }
        return Optional.empty();
      }
    } catch (SQLException exception) {
      throw new PersonaPersistenceException("consultar por ID", exception);
    }
  }

  @Override
  public List<PersonaModel> getAll() {
    try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
        ResultSet resultSet = statement.executeQuery()) {
      return PersonaPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (SQLException exception) {
      throw new PersonaPersistenceException("listar", exception);
    }
  }

  @Override
  public void delete(final PersonaId personaId) {
    try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
      statement.setInt(1, personaId.value());
      statement.executeUpdate();
    } catch (SQLException exception) {
      throw new PersonaPersistenceException("eliminar", exception);
    }
  }

  private static void bindFields(
      final PreparedStatement statement,
      final PersonaPersistenceDto dto,
      final int offset)
      throws SQLException {

    statement.setString(offset, dto.nombres());
    statement.setString(offset + 1, dto.apellidos());
    statement.setDate(offset + 2, Date.valueOf(dto.fechaNacimiento()));
    statement.setInt(offset + 3, dto.idMunicipioNacimiento());
    statement.setInt(offset + 4, dto.edad());
    statement.setBigDecimal(offset + 5, dto.estatura());
    statement.setString(offset + 6, dto.sexo());
    statement.setString(offset + 7, dto.nivelEstudios());
    statement.setString(offset + 8, dto.situacionMilitar());
    statement.setString(offset + 9, dto.dni());
    statement.setString(offset + 10, dto.estadoCivil());
    statement.setString(offset + 11, dto.ocupacion());
    statement.setString(offset + 12, dto.grupoEtnico());
  }
}
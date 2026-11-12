package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildAddress;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ChildPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ChildEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests para ChildPersistenceMapper: modelo a DTO, entidad a modelo y lectura desde un ResultSet,
 * incluyendo el manejo de los campos opcionales ausentes (null).
 */
@DisplayName("ChildPersistenceMapper")
class ChildPersistenceMapperTest {

  @Test
  @DisplayName("fromModelToDto() convierte fechas a texto y conserva los opcionales presentes")
  void shouldConvertFullModelToDto() {
    // Arrange
    final ChildModel model =
        new ChildModel(
            new ChildId("M-001"),
            new ChildFullName("Ana", "Maria", "Gomez", "Perez"),
            LocalDate.of(2022, 3, 10),
            LocalDate.of(2024, 1, 15),
            LocalDate.of(2024, 12, 20),
            new ChildAddress("Calle 10 # 5-20"),
            new GuardianId("1001"),
            GuardianRelationship.FAMILIAR);

    // Act
    final ChildPersistenceDto dto = ChildPersistenceMapper.fromModelToDto(model);

    // Assert
    assertEquals("M-001", dto.id());
    assertEquals("Maria", dto.secondName());
    assertEquals("2022-03-10", dto.birthDate());
    assertEquals("2024-01-15", dto.admissionDate());
    assertEquals("2024-12-20", dto.dischargeDate());
    assertEquals("Calle 10 # 5-20", dto.address());
    assertEquals("1001", dto.guardianId());
    assertEquals("FAMILIAR", dto.guardianRelationship());
  }

  @Test
  @DisplayName("fromModelToDto() deja en null los campos opcionales ausentes")
  void shouldKeepOptionalFieldsNullInDto() {
    // Arrange
    final ChildModel model =
        ChildModel.create(
            new ChildId("M-002"),
            new ChildFullName("Luis", null, "Rios", null),
            LocalDate.of(2021, 5, 1),
            LocalDate.of(2024, 2, 1),
            null,
            new GuardianId("1002"),
            GuardianRelationship.CONOCIDO);

    // Act
    final ChildPersistenceDto dto = ChildPersistenceMapper.fromModelToDto(model);

    // Assert
    assertNull(dto.secondName());
    assertNull(dto.secondSurname());
    assertNull(dto.dischargeDate());
    assertNull(dto.address());
  }

  @Test
  @DisplayName("fromEntityToModel() reconstruye el modelo y respeta los opcionales null")
  void shouldBuildModelFromEntityWithNullOptionals() {
    // Arrange
    final ChildEntity entity =
        new ChildEntity(
            "M-003",
            "Sofia",
            null,
            "Diaz",
            null,
            "2020-08-09",
            "2023-01-10",
            null,
            null,
            "1001",
            "FAMILIAR");

    // Act
    final ChildModel model = ChildPersistenceMapper.fromEntityToModel(entity);

    // Assert
    assertEquals("M-003", model.getId().value());
    assertEquals(LocalDate.of(2020, 8, 9), model.getBirthDate());
    assertNull(model.getDischargeDate());
    assertNull(model.getAddress());
    assertNull(model.getFullName().secondName());
  }

  @Test
  @DisplayName("fromResultSetToEntity() lee las 11 columnas de la tabla nino")
  void shouldReadEntityFromResultSet() throws SQLException {
    // Arrange
    final ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString("matricula")).thenReturn("M-004");
    when(resultSet.getString("primer_nombre")).thenReturn("Ana");
    when(resultSet.getString("segundo_nombre")).thenReturn(null);
    when(resultSet.getString("primer_apellido")).thenReturn("Gomez");
    when(resultSet.getString("segundo_apellido")).thenReturn(null);
    when(resultSet.getString("fecha_nacimiento")).thenReturn("2022-03-10");
    when(resultSet.getString("fecha_ingreso")).thenReturn("2024-01-15");
    when(resultSet.getString("fecha_baja")).thenReturn(null);
    when(resultSet.getString("direccion")).thenReturn(null);
    when(resultSet.getString("ci_responsable")).thenReturn("1001");
    when(resultSet.getString("relacion_responsable")).thenReturn("FAMILIAR");

    // Act
    final ChildEntity entity = ChildPersistenceMapper.fromResultSetToEntity(resultSet);

    // Assert
    assertEquals("M-004", entity.id());
    assertEquals("2022-03-10", entity.birthDate());
    assertNull(entity.dischargeDate());
    assertEquals("1001", entity.guardianId());
    assertEquals("FAMILIAR", entity.guardianRelationship());
  }
}

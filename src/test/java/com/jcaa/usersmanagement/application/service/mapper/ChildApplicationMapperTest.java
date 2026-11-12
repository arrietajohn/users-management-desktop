package com.jcaa.usersmanagement.application.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.exception.InvalidChildDatesException;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Tests para ChildApplicationMapper: conversión de comandos y consultas al dominio. */
@DisplayName("ChildApplicationMapper")
class ChildApplicationMapperTest {

  private static CreateChildCommand createCommand(final String address, final String birthDate) {
    return new CreateChildCommand(
        "M-001",
        "Ana",
        "Maria",
        "Gomez",
        "Perez",
        birthDate,
        "2024-01-15",
        address,
        "1001",
        "familiar");
  }

  @Test
  @DisplayName("fromCreateCommandToModel() arma el modelo con los datos del comando")
  void shouldBuildModelFromCreateCommand() {
    // Act
    final ChildModel model =
        ChildApplicationMapper.fromCreateCommandToModel(
            createCommand("Calle 10 # 5-20", "2022-03-10"));

    // Assert
    assertEquals("M-001", model.getId().value());
    assertEquals("Ana", model.getFullName().firstName());
    assertEquals(LocalDate.of(2022, 3, 10), model.getBirthDate());
    assertEquals(LocalDate.of(2024, 1, 15), model.getAdmissionDate());
    assertNull(model.getDischargeDate());
    assertEquals("Calle 10 # 5-20", model.getAddress().value());
    assertEquals(GuardianRelationship.FAMILIAR, model.getGuardianRelationship());
    assertTrue(model.isActive());
  }

  @Test
  @DisplayName("fromCreateCommandToModel() deja la dirección en null cuando viene vacía")
  void shouldLeaveAddressNullWhenBlank() {
    // Act
    final ChildModel model =
        ChildApplicationMapper.fromCreateCommandToModel(createCommand("   ", "2022-03-10"));

    // Assert
    assertNull(model.getAddress());
  }

  @Test
  @DisplayName("fromCreateCommandToModel() lanza InvalidChildDatesException con una fecha inexistente")
  void shouldThrowWhenDateDoesNotExist() {
    // Act & Assert
    assertThrows(
        InvalidChildDatesException.class,
        () -> ChildApplicationMapper.fromCreateCommandToModel(createCommand(null, "2022-02-30")));
  }

  @Test
  @DisplayName("fromUpdateCommandToModel() conserva la fecha de baja cuando se envía")
  void shouldBuildModelFromUpdateCommandWithDischargeDate() {
    // Arrange
    final UpdateChildCommand command =
        new UpdateChildCommand(
            "M-001",
            "Ana",
            null,
            "Gomez",
            null,
            "2022-03-10",
            "2024-01-15",
            "2024-12-20",
            null,
            "1001",
            "CONOCIDO");

    // Act
    final ChildModel model = ChildApplicationMapper.fromUpdateCommandToModel(command);

    // Assert
    assertEquals(LocalDate.of(2024, 12, 20), model.getDischargeDate());
    assertEquals(GuardianRelationship.CONOCIDO, model.getGuardianRelationship());
    assertNull(model.getFullName().secondName());
  }

  @Test
  @DisplayName("fromGetChildByIdQueryToChildId() y fromDeleteCommandToChildId() devuelven el ChildId")
  void shouldConvertQueryAndDeleteCommandToChildId() {
    // Act & Assert
    assertEquals(
        new ChildId("M-001"),
        ChildApplicationMapper.fromGetChildByIdQueryToChildId(new GetChildByIdQuery(" M-001 ")));
    assertEquals(
        new ChildId("M-002"),
        ChildApplicationMapper.fromDeleteCommandToChildId(new DeleteChildCommand("M-002")));
  }
}

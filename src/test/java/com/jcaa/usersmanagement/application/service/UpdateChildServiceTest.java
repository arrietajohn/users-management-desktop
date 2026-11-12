package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateChildPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.domain.exception.InvalidChildDatesException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para UpdateChildService.
 *
 * <p>Cubre: flujo feliz, niño no encontrado, comando inválido y fechas que rompen la invariante.
 */
@DisplayName("UpdateChildService")
@ExtendWith(MockitoExtension.class)
class UpdateChildServiceTest {

  @Mock private UpdateChildPort updateChildPort;
  @Mock private GetChildByIdPort getChildByIdPort;

  private UpdateChildService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateChildService(updateChildPort, getChildByIdPort, validatorFactory.getValidator());
    }
  }

  private static ChildModel existingChild() {
    return new ChildModel(
        new ChildId("M-001"),
        new ChildFullName("Ana", "Maria", "Gomez", "Perez"),
        LocalDate.of(2022, 3, 10),
        LocalDate.of(2024, 1, 15),
        null,
        null,
        new GuardianId("1001"),
        GuardianRelationship.FAMILIAR);
  }

  private static UpdateChildCommand command(
      final String birthDate, final String admissionDate, final String dischargeDate) {
    return new UpdateChildCommand(
        "M-001",
        "Ana",
        null,
        "Lopez",
        null,
        birthDate,
        admissionDate,
        dischargeDate,
        "Calle 10 # 5-20",
        "1002",
        "CONOCIDO");
  }

  @Test
  @DisplayName("execute() actualiza el niño existente y devuelve el resultado")
  void shouldUpdateChildWhenItExists() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.of(existingChild()));
    when(updateChildPort.update(any(ChildModel.class))).thenAnswer(call -> call.getArgument(0));

    // Act
    final ChildModel result = service.execute(command("2022-03-10", "2024-01-15", "2024-12-20"));

    // Assert
    assertEquals("Lopez", result.getFullName().firstSurname());
    assertEquals(LocalDate.of(2024, 12, 20), result.getDischargeDate());
    assertEquals(GuardianRelationship.CONOCIDO, result.getGuardianRelationship());
    verify(updateChildPort).update(any(ChildModel.class));
  }

  @Test
  @DisplayName("execute() deja la fecha de baja en null cuando no se envía")
  void shouldKeepDischargeDateNullWhenNotProvided() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.of(existingChild()));
    when(updateChildPort.update(any(ChildModel.class))).thenAnswer(call -> call.getArgument(0));

    // Act
    final ChildModel result = service.execute(command("2022-03-10", "2024-01-15", null));

    // Assert
    assertNull(result.getDischargeDate());
  }

  @Test
  @DisplayName("execute() lanza ChildNotFoundException cuando la matrícula no existe")
  void shouldThrowWhenChildDoesNotExist() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        ChildNotFoundException.class,
        () -> service.execute(command("2022-03-10", "2024-01-15", null)));
    verify(updateChildPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el comando es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final UpdateChildCommand invalid =
        new UpdateChildCommand(" ", "", null, "", null, "", "", "2024/12/20", null, "", "");

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(invalid));
    verifyNoInteractions(updateChildPort, getChildByIdPort);
  }

  @Test
  @DisplayName("execute() lanza InvalidChildDatesException cuando el nacimiento no es anterior al ingreso")
  void shouldThrowWhenBirthDateIsNotBeforeAdmissionDate() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.of(existingChild()));

    // Act & Assert
    assertThrows(
        InvalidChildDatesException.class,
        () -> service.execute(command("2025-01-01", "2024-01-15", null)));
    verify(updateChildPort, never()).update(any());
  }
}

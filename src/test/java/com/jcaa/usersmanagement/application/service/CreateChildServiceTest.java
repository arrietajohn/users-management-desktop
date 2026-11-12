package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveChildPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import com.jcaa.usersmanagement.domain.exception.ChildAlreadyExistsException;
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
 * Tests para CreateChildService.
 *
 * <p>Cubre: flujo feliz, matrícula ya existente, comando inválido y fecha con formato inválido.
 */
@DisplayName("CreateChildService")
@ExtendWith(MockitoExtension.class)
class CreateChildServiceTest {

  @Mock private SaveChildPort saveChildPort;
  @Mock private GetChildByIdPort getChildByIdPort;

  private CreateChildService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateChildService(saveChildPort, getChildByIdPort, validatorFactory.getValidator());
    }
  }

  private static CreateChildCommand validCommand(final String birthDate) {
    return new CreateChildCommand(
        "M-001",
        "Ana",
        "Maria",
        "Gomez",
        "Perez",
        birthDate,
        "2024-01-15",
        null,
        "1001",
        "FAMILIAR");
  }

  // ── flujo feliz

  @Test
  @DisplayName("execute() guarda y devuelve el niño cuando la matrícula no existe")
  void shouldSaveChildWhenIdIsNotTaken() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.empty());
    when(saveChildPort.save(any(ChildModel.class))).thenAnswer(call -> call.getArgument(0));

    // Act
    final ChildModel result = service.execute(validCommand("2022-03-10"));

    // Assert
    assertEquals("M-001", result.getId().value());
    assertEquals(LocalDate.of(2022, 3, 10), result.getBirthDate());
    assertEquals(GuardianRelationship.FAMILIAR, result.getGuardianRelationship());
    verify(saveChildPort).save(any(ChildModel.class));
  }

  // ── matrícula ya existente

  @Test
  @DisplayName("execute() lanza ChildAlreadyExistsException cuando la matrícula ya existe")
  void shouldThrowWhenIdAlreadyExists() {
    // Arrange
    final ChildModel existing =
        new ChildModel(
            new ChildId("M-001"),
            new ChildFullName("Ana", null, "Gomez", null),
            LocalDate.of(2022, 3, 10),
            LocalDate.of(2024, 1, 15),
            null,
            null,
            new GuardianId("1001"),
            GuardianRelationship.FAMILIAR);
    when(getChildByIdPort.getById(any())).thenReturn(Optional.of(existing));

    // Act & Assert
    assertThrows(
        ChildAlreadyExistsException.class, () -> service.execute(validCommand("2022-03-10")));
    verify(saveChildPort, never()).save(any());
  }

  // ── validación del comando

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el comando es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final CreateChildCommand invalid =
        new CreateChildCommand(" ", "", null, "", null, "10-03-2022", "", null, "", "");

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(invalid));
    verifyNoInteractions(saveChildPort, getChildByIdPort);
  }

  // ── fecha con formato correcto pero inexistente

  @Test
  @DisplayName("execute() lanza InvalidChildDatesException cuando la fecha no existe en el calendario")
  void shouldThrowWhenDateDoesNotExist() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        InvalidChildDatesException.class, () -> service.execute(validCommand("2022-02-30")));
    verify(saveChildPort, never()).save(any());
  }
}

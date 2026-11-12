package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
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

/** Tests para GetChildByIdService: flujo feliz, niño no encontrado y consulta inválida. */
@DisplayName("GetChildByIdService")
@ExtendWith(MockitoExtension.class)
class GetChildByIdServiceTest {

  @Mock private GetChildByIdPort getChildByIdPort;

  private GetChildByIdService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new GetChildByIdService(getChildByIdPort, validatorFactory.getValidator());
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

  @Test
  @DisplayName("execute() devuelve el niño cuando existe")
  void shouldReturnChildWhenItExists() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.of(existingChild()));

    // Act
    final ChildModel result = service.execute(new GetChildByIdQuery("M-001"));

    // Assert
    assertEquals("M-001", result.getId().value());
    assertEquals(LocalDate.of(2024, 1, 15), result.getAdmissionDate());
  }

  @Test
  @DisplayName("execute() lanza ChildNotFoundException cuando la matrícula no existe")
  void shouldThrowWhenChildDoesNotExist() {
    // Arrange
    when(getChildByIdPort.getById(any())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        ChildNotFoundException.class, () -> service.execute(new GetChildByIdQuery("no-existe")));
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el id está en blanco")
  void shouldThrowWhenQueryIsInvalid() {
    // Act & Assert
    assertThrows(
        ConstraintViolationException.class, () -> service.execute(new GetChildByIdQuery("  ")));
    verifyNoInteractions(getChildByIdPort);
  }
}

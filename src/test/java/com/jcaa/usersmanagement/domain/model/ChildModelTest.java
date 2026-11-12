
package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.ChildName;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ChildModel")
class ChildModelTest {

  private ChildId childId;
  private ChildName childName;
  private LocalDate birthDate;
  private LocalDate admissionDate;

  @BeforeEach
  void setUp() {
    childId = new ChildId("child-001");
    childName = new ChildName("Juan Carlos");
    birthDate = LocalDate.now().minusYears(5);
    admissionDate = LocalDate.now().minusYears(1);
  }

  // --- create() ---

  @Test
  @DisplayName("create() debe crear un niño y preservar sus datos")
  void shouldCreateChildAndPreserveAllFields() {
    // Act
    final ChildModel child =
        ChildModel.create(childId, childName, birthDate, admissionDate);

    // Assert
    assertAll(
        "create() factory",
        () -> assertEquals(childId, child.getId()),
        () -> assertEquals(childName, child.getName()),
        () -> assertEquals(birthDate, child.getBirthDate()),
        () -> assertEquals(admissionDate, child.getAdmissionDate()),
        () -> assertNull(child.getExitDate()));
  }

  @Test
  @DisplayName("create() debe rechazar una fecha de nacimiento futura")
  void shouldRejectFutureBirthDate() {
    final LocalDate futureBirthDate = LocalDate.now().plusDays(1);

    assertThrows(
        IllegalArgumentException.class,
        () -> ChildModel.create(childId, childName, futureBirthDate, admissionDate));
  }

  @Test
  @DisplayName("create() debe rechazar una fecha de ingreso anterior al nacimiento")
  void shouldRejectAdmissionDateBeforeBirthDate() {
    final LocalDate invalidAdmissionDate = birthDate.minusDays(1);

    assertThrows(
        IllegalArgumentException.class,
        () -> ChildModel.create(childId, childName, birthDate, invalidAdmissionDate));
  }

  // --- registerExit() ---

  @Test
  @DisplayName("registerExit() debe registrar la fecha de salida")
  void shouldRegisterExitDateAndPreserveOtherFields() {
    // Arrange
    final ChildModel child =
        ChildModel.create(childId, childName, birthDate, admissionDate);
    final LocalDate exitDate = LocalDate.now();

    // Act
    final ChildModel childWithExit = child.registerExit(exitDate);

    // Assert
    assertAll(
        "registerExit() result",
        () -> assertNotSame(child, childWithExit),
        () -> assertEquals(exitDate, childWithExit.getExitDate()),
        () -> assertEquals(childId, childWithExit.getId()),
        () -> assertEquals(childName, childWithExit.getName()),
        () -> assertEquals(birthDate, childWithExit.getBirthDate()),
        () -> assertEquals(admissionDate, childWithExit.getAdmissionDate()));
  }

  @Test
  @DisplayName("registerExit() debe rechazar una fecha anterior al ingreso")
  void shouldRejectExitDateBeforeAdmissionDate() {
    // Arrange
    final ChildModel child =
        ChildModel.create(childId, childName, birthDate, admissionDate);
    final LocalDate invalidExitDate = admissionDate.minusDays(1);

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> child.registerExit(invalidExitDate));
  }

  // --- update() ---

  @Test
  @DisplayName("update() debe actualizar los datos del niño")
  void shouldUpdateChildData() {
    // Arrange
    final ChildModel child =
        ChildModel.create(childId, childName, birthDate, admissionDate);
    final ChildName updatedName = new ChildName("Maria Fernanda");
    final LocalDate updatedBirthDate = LocalDate.now().minusYears(6);

    // Act
    final ChildModel updatedChild =
        child.update(updatedName, updatedBirthDate, admissionDate, null);

    // Assert
    assertAll(
        "update() result",
        () -> assertNotSame(child, updatedChild),
        () -> assertEquals(childId, updatedChild.getId()),
        () -> assertEquals(updatedName, updatedChild.getName()),
        () -> assertEquals(updatedBirthDate, updatedChild.getBirthDate()),
        () -> assertEquals(admissionDate, updatedChild.getAdmissionDate()),
        () -> assertNull(updatedChild.getExitDate()));
  }
}

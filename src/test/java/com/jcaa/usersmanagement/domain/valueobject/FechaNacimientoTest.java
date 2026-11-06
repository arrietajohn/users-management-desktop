package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FechaNacimiento")
class FechaNacimientoTest {

  @Test
  @DisplayName("Debe crear una fecha de nacimiento válida")
  void shouldCreateValidBirthDate() {
    LocalDate fecha = LocalDate.of(2000, 5, 15);
    FechaNacimiento nacimiento = new FechaNacimiento(fecha);

    assertEquals(fecha, nacimiento.value());
    assertEquals("2000-05-15", nacimiento.toString());
  }

  @Test
  @DisplayName("Debe rechazar una fecha nula")
  void shouldRejectNullBirthDate() {
    assertThrows(
        NullPointerException.class,
        () -> new FechaNacimiento(null));
  }

  @Test
  @DisplayName("Debe rechazar una fecha futura")
  void shouldRejectFutureBirthDate() {
    assertThrows(
        com.jcaa.usersmanagement.domain.exception.InvalidPersonaException.class,
        () -> new FechaNacimiento(LocalDate.now().plusDays(1)));
  }

  @Test
  @DisplayName("Debe permitir la fecha actual")
  void shouldAllowToday() {
    FechaNacimiento nacimiento = new FechaNacimiento(LocalDate.now());

    assertEquals(LocalDate.now(), nacimiento.value());
  }
}

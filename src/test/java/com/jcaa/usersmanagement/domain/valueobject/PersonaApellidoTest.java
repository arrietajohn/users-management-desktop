package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PersonaApellido")
class PersonaApellidoTest {

  @Test
  @DisplayName("Debe crear un apellido válido")
  void shouldCreateValidLastName() {
    PersonaApellido apellido = new PersonaApellido(" García Romero ");

    assertEquals("García Romero", apellido.value());
    assertEquals("García Romero", apellido.toString());
  }

  @Test
  @DisplayName("Debe rechazar un apellido nulo")
  void shouldRejectNullLastName() {
    assertThrows(
        NullPointerException.class,
        () -> new PersonaApellido(null));
  }

  @Test
  @DisplayName("Debe rechazar un apellido vacío")
  void shouldRejectEmptyLastName() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaApellido("   "));
  }

  @Test
  @DisplayName("Debe rechazar apellidos con menos de tres caracteres")
  void shouldRejectShortLastName() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaApellido("Ru"));
  }
}

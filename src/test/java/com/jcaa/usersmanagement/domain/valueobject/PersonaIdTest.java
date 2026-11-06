package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PersonaId")
class PersonaIdTest {

  @Test
  @DisplayName("Debe crear un identificador válido")
  void shouldCreateValidId() {
    PersonaId id = new PersonaId(1);

    assertEquals(1, id.value());
    assertEquals("1", id.toString());
  }

  @Test
  @DisplayName("Debe rechazar un identificador nulo")
  void shouldRejectNullId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaId(null));
  }

  @Test
  @DisplayName("Debe rechazar un identificador igual a cero")
  void shouldRejectZeroId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaId(0));
  }

  @Test
  @DisplayName("Debe rechazar un identificador negativo")
  void shouldRejectNegativeId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaId(-1));
  }
}

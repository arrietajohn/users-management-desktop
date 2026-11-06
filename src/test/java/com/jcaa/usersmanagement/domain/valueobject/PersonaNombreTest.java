package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PersonaNombre")
class PersonaNombreTest {

  @Test
  @DisplayName("Debe crear un nombre válido")
  void shouldCreateValidName() {
    PersonaNombre nombre = new PersonaNombre(" Stivenson ");

    assertEquals("Stivenson", nombre.value());
    assertEquals("Stivenson", nombre.toString());
  }

  @Test
  @DisplayName("Debe rechazar un nombre nulo")
  void shouldRejectNullName() {
    assertThrows(
        NullPointerException.class,
        () -> new PersonaNombre(null));
  }

  @Test
  @DisplayName("Debe rechazar un nombre vacío")
  void shouldRejectEmptyName() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaNombre("   "));
  }

  @Test
  @DisplayName("Debe rechazar nombres con menos de tres caracteres")
  void shouldRejectShortName() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaNombre("Al"));
  }
}
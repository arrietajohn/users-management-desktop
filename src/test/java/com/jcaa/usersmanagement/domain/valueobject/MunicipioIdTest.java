package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MunicipioId")
class MunicipioIdTest {

  @Test
  @DisplayName("Debe crear un identificador de municipio válido")
  void shouldCreateValidMunicipalityId() {
    MunicipioId id = new MunicipioId(1);

    assertEquals(1, id.value());
    assertEquals("1", id.toString());
  }

  @Test
  @DisplayName("Debe rechazar un identificador nulo")
  void shouldRejectNullMunicipalityId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new MunicipioId(null));
  }

  @Test
  @DisplayName("Debe rechazar un identificador igual a cero")
  void shouldRejectZeroMunicipalityId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new MunicipioId(0));
  }

  @Test
  @DisplayName("Debe rechazar un identificador negativo")
  void shouldRejectNegativeMunicipalityId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new MunicipioId(-1));
  }
}
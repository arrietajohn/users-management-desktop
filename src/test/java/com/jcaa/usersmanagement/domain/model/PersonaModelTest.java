package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import com.jcaa.usersmanagement.domain.valueobject.FechaNacimiento;
import com.jcaa.usersmanagement.domain.valueobject.MunicipioId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaApellido;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaNombre;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PersonaModel")
class PersonaModelTest {

  private PersonaModel crearPersona() {
    return new PersonaModel(
        new PersonaId(1),
        new PersonaNombre("Stivenson"),
        new PersonaApellido("García Romero"),
        new FechaNacimiento(LocalDate.of(2000, 5, 15)),
        new MunicipioId(1),
        26,
        new BigDecimal("1.75"),
        "Masculino",
        "Universitario",
        null,
        "123456",
        "Soltero",
        "Estudiante",
        null);
  }

  @Test
  @DisplayName("Debe crear una persona con datos válidos")
  void shouldCreatePersonaWithValidData() {
    PersonaModel persona = crearPersona();

    assertAll(
        () -> assertEquals(1, persona.getIdPersona().value()),
        () -> assertEquals("Stivenson", persona.getNombres().value()),
        () -> assertEquals("García Romero", persona.getApellidos().value()),
        () -> assertEquals(26, persona.getEdad()),
        () -> assertEquals("Masculino", persona.getSexo()),
        () -> assertNull(persona.getSituacionMilitar()),
        () -> assertNull(persona.getGrupoEtnico()));
  }

  @Test
  @DisplayName("Debe rechazar un identificador nulo")
  void shouldRejectNullId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            null, new PersonaNombre("Juan"), new PersonaApellido("Pérez"),
            new FechaNacimiento(LocalDate.of(2000, 1, 1)),
            new MunicipioId(1), 26, null, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe rechazar nombres nulos")
  void shouldRejectNullName() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            new PersonaId(1), null, new PersonaApellido("Pérez"),
            new FechaNacimiento(LocalDate.of(2000, 1, 1)),
            new MunicipioId(1), 26, null, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe rechazar apellidos nulos")
  void shouldRejectNullLastName() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            new PersonaId(1), new PersonaNombre("Juan"), null,
            new FechaNacimiento(LocalDate.of(2000, 1, 1)),
            new MunicipioId(1), 26, null, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe rechazar fechas de nacimiento nulas")
  void shouldRejectNullBirthDate() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            new PersonaId(1), new PersonaNombre("Juan"), new PersonaApellido("Pérez"),
            null, new MunicipioId(1), 26, null, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe rechazar municipios nulos")
  void shouldRejectNullMunicipalityId() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            new PersonaId(1), new PersonaNombre("Juan"), new PersonaApellido("Pérez"),
            new FechaNacimiento(LocalDate.of(2000, 1, 1)),
            null, 26, null, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe rechazar edades negativas")
  void shouldRejectNegativeAge() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            new PersonaId(1), new PersonaNombre("Juan"), new PersonaApellido("Pérez"),
            new FechaNacimiento(LocalDate.of(2000, 1, 1)),
            new MunicipioId(1), -1, null, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe rechazar estaturas iguales o menores que cero")
  void shouldRejectInvalidHeight() {
    assertThrows(
        InvalidPersonaException.class,
        () -> new PersonaModel(
            new PersonaId(1), new PersonaNombre("Juan"), new PersonaApellido("Pérez"),
            new FechaNacimiento(LocalDate.of(2000, 1, 1)),
            new MunicipioId(1), 26, BigDecimal.ZERO, null, null, null, null, null, null, null));
  }

  @Test
  @DisplayName("Debe permitir atributos opcionales nulos")
  void shouldAllowNullOptionalFields() {
    PersonaModel persona = crearPersona();

    assertAll(
        () -> assertNull(persona.getSituacionMilitar()),
        () -> assertNull(persona.getGrupoEtnico()),
        () -> assertNotNull(persona.getFechaNacimiento()));
  }
}

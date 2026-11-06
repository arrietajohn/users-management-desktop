package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import com.jcaa.usersmanagement.domain.valueobject.FechaNacimiento;
import com.jcaa.usersmanagement.domain.valueobject.MunicipioId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaApellido;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaNombre;
import java.math.BigDecimal;
import lombok.Value;

@Value
public class PersonaModel {

  PersonaId idPersona;
  PersonaNombre nombres;
  PersonaApellido apellidos;
  FechaNacimiento fechaNacimiento;
  MunicipioId idMunicipioNacimiento;
  Integer edad;
  BigDecimal estatura;
  String sexo;
  String nivelEstudios;
  String situacionMilitar;
  String dni;
  String estadoCivil;
  String ocupacion;
  String grupoEtnico;

  public PersonaModel(
      final PersonaId idPersona,
      final PersonaNombre nombres,
      final PersonaApellido apellidos,
      final FechaNacimiento fechaNacimiento,
      final MunicipioId idMunicipioNacimiento,
      final Integer edad,
      final BigDecimal estatura,
      final String sexo,
      final String nivelEstudios,
      final String situacionMilitar,
      final String dni,
      final String estadoCivil,
      final String ocupacion,
      final String grupoEtnico) {

    if (idPersona == null) {
      throw InvalidPersonaException.becauseIdIsInvalid();
    }

    if (nombres == null) {
      throw InvalidPersonaException.becauseRequiredFieldIsEmpty("nombres");
    }

    if (apellidos == null) {
      throw InvalidPersonaException.becauseRequiredFieldIsEmpty("apellidos");
    }

    if (fechaNacimiento == null) {
      throw InvalidPersonaException.becauseRequiredFieldIsEmpty("fechaNacimiento");
    }

    if (idMunicipioNacimiento == null) {
      throw InvalidPersonaException.becauseMunicipalityIdIsInvalid();
    }

    validateAge(edad);
    validateHeight(estatura);

    this.idPersona = idPersona;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.fechaNacimiento = fechaNacimiento;
    this.idMunicipioNacimiento = idMunicipioNacimiento;
    this.edad = edad;
    this.estatura = estatura;
    this.sexo = normalizeOptional(sexo);
    this.nivelEstudios = normalizeOptional(nivelEstudios);
    this.situacionMilitar = normalizeOptional(situacionMilitar);
    this.dni = normalizeOptional(dni);
    this.estadoCivil = normalizeOptional(estadoCivil);
    this.ocupacion = normalizeOptional(ocupacion);
    this.grupoEtnico = normalizeOptional(grupoEtnico);
  }

  private static void validateAge(final Integer age) {
    if (age == null || age < 0) {
      throw InvalidPersonaException.becauseAgeIsInvalid();
    }
  }

  private static void validateHeight(final BigDecimal height) {
    if (height != null && height.compareTo(BigDecimal.ZERO) <= 0) {
      throw InvalidPersonaException.becauseHeightIsInvalid();
    }
  }

  private static String normalizeOptional(final String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }

    return value.trim();
  }
}
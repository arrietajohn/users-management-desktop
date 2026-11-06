package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonaPersistenceDto(
    Integer idPersona,
    String nombres,
    String apellidos,
    LocalDate fechaNacimiento,
    Integer idMunicipioNacimiento,
    Integer edad,
    BigDecimal estatura,
    String sexo,
    String nivelEstudios,
    String situacionMilitar,
    String dni,
    String estadoCivil,
    String ocupacion,
    String grupoEtnico) {}
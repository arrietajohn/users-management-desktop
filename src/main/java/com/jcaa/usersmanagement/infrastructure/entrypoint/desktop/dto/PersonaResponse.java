package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonaResponse(
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
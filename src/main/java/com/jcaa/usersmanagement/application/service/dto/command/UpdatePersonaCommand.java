package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatePersonaCommand(
    @NotNull @Positive Integer idPersona,
    @NotBlank @Size(min = 3) String nombres,
    @NotBlank @Size(min = 3) String apellidos,
    @NotNull @PastOrPresent LocalDate fechaNacimiento,
    @NotNull @Positive Integer idMunicipioNacimiento,
    @NotNull @PositiveOrZero Integer edad,
    @DecimalMin(value = "0.0", inclusive = false) BigDecimal estatura,
    String sexo,
    String nivelEstudios,
    String situacionMilitar,
    String dni,
    String estadoCivil,
    String ocupacion,
    String grupoEtnico
) {
}

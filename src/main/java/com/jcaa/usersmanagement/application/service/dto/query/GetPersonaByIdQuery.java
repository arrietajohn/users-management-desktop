package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GetPersonaByIdQuery(
    @NotNull @Positive Integer id
) {
}

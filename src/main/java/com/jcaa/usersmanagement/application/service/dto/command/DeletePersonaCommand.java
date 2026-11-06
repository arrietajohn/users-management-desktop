package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeletePersonaCommand(
    @NotNull @Positive Integer id
) {
}

package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;

public record DeleteChildCommand(
    @NotBlank(message = "id must not be blank") String id
) {
}

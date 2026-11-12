package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateChildCommand(
    @NotBlank(message = "id must not be blank") String id,
    @NotBlank(message = "first name must not be blank") String firstName,
    String secondName,
    @NotBlank(message = "first surname must not be blank") String firstSurname,
    String secondSurname,
    @NotBlank(message = "birth date must not be blank")
        @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "birth date must have the format yyyy-MM-dd")
        String birthDate,
    @NotBlank(message = "admission date must not be blank")
        @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "admission date must have the format yyyy-MM-dd")
        String admissionDate,
    String address,
    @NotBlank(message = "guardian id must not be blank") String guardianId,
    @NotBlank(message = "guardian relationship must not be blank") String guardianRelationship)
{
}

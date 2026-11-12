package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record CreateChildRequest(
    String id,
    String firstName,
    String secondName,
    String firstSurname,
    String secondSurname,
    String birthDate,
    String admissionDate,
    String address,
    String guardianId,
    String guardianRelationship) {}

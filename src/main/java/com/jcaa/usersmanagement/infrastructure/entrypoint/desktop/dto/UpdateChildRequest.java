package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record UpdateChildRequest(
    String id,
    String firstName,
    String secondName,
    String firstSurname,
    String secondSurname,
    String birthDate,
    String admissionDate,
    String dischargeDate,
    String address,
    String guardianId,
    String guardianRelationship) {}

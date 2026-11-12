package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record ChildResponse(
    String id,
    String fullName,
    String birthDate,
    String admissionDate,
    String dischargeDate,
    String address,
    String guardianId,
    String guardianRelationship,
    String status) {}

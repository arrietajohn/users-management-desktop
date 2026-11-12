package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record ChildPersistenceDto(
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

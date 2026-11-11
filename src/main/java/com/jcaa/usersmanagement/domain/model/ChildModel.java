package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.exception.InvalidChildDatesException;
import com.jcaa.usersmanagement.domain.valueobject.ChildAddress;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Value;

@Value
public class ChildModel {

  ChildId id;
  ChildFullName fullName;
  LocalDate birthDate;
  LocalDate admissionDate;
  LocalDate dischargeDate;
  ChildAddress address;
  GuardianId guardianId;
  GuardianRelationship guardianRelationship;

  public ChildModel(
      final ChildId id,
      final ChildFullName fullName,
      final LocalDate birthDate,
      final LocalDate admissionDate,
      final LocalDate dischargeDate,
      final ChildAddress address,
      final GuardianId guardianId,
      final GuardianRelationship guardianRelationship) {
    this.id = Objects.requireNonNull(id, "ChildId cannot be null");
    this.fullName = Objects.requireNonNull(fullName, "ChildFullName cannot be null");
    this.birthDate = validateBirthDate(birthDate);
    this.admissionDate = validateAdmissionDate(admissionDate);
    validateBirthDateBeforeAdmissionDate(this.birthDate, this.admissionDate);
    validateDischargeDateNotBeforeAdmissionDate(dischargeDate, this.admissionDate);
    this.dischargeDate = dischargeDate;
    this.address = address;
    this.guardianId = Objects.requireNonNull(guardianId, "GuardianId cannot be null");
    this.guardianRelationship =
        Objects.requireNonNull(guardianRelationship, "GuardianRelationship cannot be null");
  }

  public static ChildModel create(
      final ChildId id,
      final ChildFullName fullName,
      final LocalDate birthDate,
      final LocalDate admissionDate,
      final ChildAddress address,
      final GuardianId guardianId,
      final GuardianRelationship guardianRelationship) {
    return new ChildModel(
        id,
        fullName,
        birthDate,
        admissionDate,
        null,
        address,
        guardianId,
        guardianRelationship);
  }

  public ChildModel withdraw(final LocalDate dischargeDate) {
    return new ChildModel(
        id,
        fullName,
        birthDate,
        admissionDate,
        dischargeDate,
        address,
        guardianId,
        guardianRelationship);
  }

  public boolean isActive() {
    return dischargeDate == null;
  }

  private static LocalDate validateBirthDate(final LocalDate birthDate) {
    if (birthDate == null) {
      throw InvalidChildDatesException.becauseBirthDateIsNull();
    }
    return birthDate;
  }

  private static LocalDate validateAdmissionDate(final LocalDate admissionDate) {
    if (admissionDate == null) {
      throw InvalidChildDatesException.becauseAdmissionDateIsNull();
    }
    return admissionDate;
  }

  private static void validateBirthDateBeforeAdmissionDate(
      final LocalDate birthDate, final LocalDate admissionDate) {
    if (!birthDate.isBefore(admissionDate)) {
      throw InvalidChildDatesException.becauseBirthDateIsNotBeforeAdmissionDate();
    }
  }

  private static void validateDischargeDateNotBeforeAdmissionDate(
      final LocalDate dischargeDate, final LocalDate admissionDate) {
    if (dischargeDate != null && dischargeDate.isBefore(admissionDate)) {
      throw InvalidChildDatesException.becauseDischargeDateIsBeforeAdmissionDate();
    }
  }
}

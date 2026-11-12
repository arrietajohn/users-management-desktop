
package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.ChildName;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Value;

@Value
public class ChildModel {

  ChildId id;
  ChildName name;
  LocalDate birthDate;
  LocalDate admissionDate;
  LocalDate exitDate;

  public static ChildModel create(
      final ChildId id,
      final ChildName name,
      final LocalDate birthDate,
      final LocalDate admissionDate) {

    validateDates(birthDate, admissionDate, null);

    return new ChildModel(id, name, birthDate, admissionDate, null);
  }

  public ChildModel registerExit(final LocalDate exitDate) {
    validateDates(birthDate, admissionDate, exitDate);

    return new ChildModel(id, name, birthDate, admissionDate, exitDate);
  }

  public ChildModel update(
      final ChildName name,
      final LocalDate birthDate,
      final LocalDate admissionDate,
      final LocalDate exitDate) {

    validateDates(birthDate, admissionDate, exitDate);

    return new ChildModel(id, name, birthDate, admissionDate, exitDate);
  }

  private static void validateDates(
      final LocalDate birthDate,
      final LocalDate admissionDate,
      final LocalDate exitDate) {

    Objects.requireNonNull(birthDate, "Birth date cannot be null");
    Objects.requireNonNull(admissionDate, "Admission date cannot be null");

    if (birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException(
          "Birth date cannot be in the future.");
    }

    if (admissionDate.isBefore(birthDate)) {
      throw new IllegalArgumentException(
          "Admission date cannot be before birth date.");
    }

    if (exitDate != null && exitDate.isBefore(admissionDate)) {
      throw new IllegalArgumentException(
          "Exit date cannot be before admission date.");
    }
  }
}

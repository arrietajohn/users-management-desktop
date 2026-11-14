package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidStudentGroupException;
import java.util.Objects;

public record StudentGroup(String value) {

  public StudentGroup {
    final String normalizedValue =
        Objects.requireNonNull(value, "StudentGroup cannot be null").trim();
    validateNotEmpty(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidStudentGroupException.becauseValueIsEmpty();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}

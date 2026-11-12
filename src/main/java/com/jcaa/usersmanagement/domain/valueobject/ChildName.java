
package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidChildNameException;
import java.util.Objects;

public record ChildName(String value) {

  private static final int MINIMUM_LENGTH = 3;

  public ChildName {
    final String normalizedValue =
        Objects.requireNonNull(value, "ChildName cannot be null").trim();

    validateNotEmpty(normalizedValue);
    validateMinimumLength(normalizedValue);

    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidChildNameException.becauseValueIsEmpty();
    }
  }

  private static void validateMinimumLength(final String normalizedValue) {
    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidChildNameException.becauseLengthIsTooShort(MINIMUM_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}

package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidChildAddressException;
import java.util.Objects;

public record ChildAddress(String value) {

  private static final int MAXIMUM_LENGTH = 150;

  public ChildAddress {
    final String normalizedValue =
        Objects.requireNonNull(value, "ChildAddress cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMaximumLength(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidChildAddressException.becauseValueIsEmpty();
    }
  }

  private static void validateMaximumLength(final String normalizedValue) {
    if (normalizedValue.length() > MAXIMUM_LENGTH) {
      throw InvalidChildAddressException.becauseLengthExceedsMaximum(MAXIMUM_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}

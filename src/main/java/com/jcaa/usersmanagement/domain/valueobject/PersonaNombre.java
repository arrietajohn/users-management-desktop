package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import java.util.Objects;

public record PersonaNombre(String value) {

  private static final int MINIMUM_LENGTH = 3;

  public PersonaNombre {
    final String normalizedValue =
        Objects.requireNonNull(value, "PersonaNombre cannot be null").trim();

    if (normalizedValue.isEmpty()) {
      throw InvalidPersonaException.becauseRequiredFieldIsEmpty("nombres");
    }

    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidPersonaException.becauseNameIsTooShort();
    }

    value = normalizedValue;
  }

  @Override
  public String toString() {
    return value;
  }
}
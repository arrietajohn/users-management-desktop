package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import java.util.Objects;

public record PersonaApellido(String value) {

  private static final int MINIMUM_LENGTH = 3;

  public PersonaApellido {
    final String normalizedValue =
        Objects.requireNonNull(value, "PersonaApellido cannot be null").trim();

    if (normalizedValue.isEmpty()) {
      throw InvalidPersonaException.becauseRequiredFieldIsEmpty("apellidos");
    }

    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidPersonaException.becauseLastNameIsTooShort();
    }

    value = normalizedValue;
  }

  @Override
  public String toString() {
    return value;
  }
}
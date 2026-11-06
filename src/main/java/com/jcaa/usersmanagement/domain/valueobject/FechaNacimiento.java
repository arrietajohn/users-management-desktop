package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;
import java.time.LocalDate;
import java.util.Objects;

public record FechaNacimiento(LocalDate value) {

  public FechaNacimiento {
    Objects.requireNonNull(value, "FechaNacimiento cannot be null");

    if (value.isAfter(LocalDate.now())) {
      throw InvalidPersonaException.becauseBirthDateIsInvalid();
    }
  }

  @Override
  public String toString() {
    return value.toString();
  }
}

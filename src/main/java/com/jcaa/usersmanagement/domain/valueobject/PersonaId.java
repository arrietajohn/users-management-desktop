package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;

public record PersonaId(Integer value) {

  public PersonaId {
    if (value == null || value <= 0) {
      throw InvalidPersonaException.becauseIdIsInvalid();
    }
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
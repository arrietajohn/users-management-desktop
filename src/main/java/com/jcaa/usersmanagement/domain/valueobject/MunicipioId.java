package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidPersonaException;

public record MunicipioId(Integer value) {

  public MunicipioId {
    if (value == null || value <= 0) {
      throw InvalidPersonaException.becauseMunicipalityIdIsInvalid();
    }
  }

  @Override
  public String toString() {
    return value.toString();
  }
}

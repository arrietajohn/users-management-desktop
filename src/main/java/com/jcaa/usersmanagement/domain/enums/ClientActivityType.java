package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidClientActivityTypeException;

/** Tipo de actividad del cliente: empresa de moda o empresa de publicidad y cine. */
public enum ClientActivityType {
  FASHION,
  ADVERTISING_FILM;

  public static ClientActivityType fromString(final String value) {
    for (final ClientActivityType type : values()) {
      if (type.name().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw InvalidClientActivityTypeException.becauseValueIsInvalid(value);
  }
}

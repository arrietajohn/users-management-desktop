package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public class PersonaPersistenceException extends RuntimeException {

  public PersonaPersistenceException(final String operation, final Throwable cause) {
    super("Error al " + operation + " Persona en MySQL.", cause);
  }
}
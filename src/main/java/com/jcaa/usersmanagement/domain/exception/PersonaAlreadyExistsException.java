package com.jcaa.usersmanagement.domain.exception;

public final class PersonaAlreadyExistsException extends DomainException {

  private static final String MESSAGE_ID_EXISTS =
      "A persona with id '%s' already exists.";

  private PersonaAlreadyExistsException(final String message) {
    super(message);
  }

  public static PersonaAlreadyExistsException becauseIdAlreadyExists(
      final Integer personaId) {
    return new PersonaAlreadyExistsException(
        String.format(MESSAGE_ID_EXISTS, personaId));
  }
}

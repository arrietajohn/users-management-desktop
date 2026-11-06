package com.jcaa.usersmanagement.domain.exception;

public final class PersonaNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID =
      "The persona with id '%s' was not found.";

  private PersonaNotFoundException(final String message) {
    super(message);
  }

  public static PersonaNotFoundException becauseIdWasNotFound(
      final Integer personaId) {
    return new PersonaNotFoundException(
        String.format(MESSAGE_BY_ID, personaId));
  }
}

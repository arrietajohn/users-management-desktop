package com.jcaa.usersmanagement.domain.exception;

public final class InvalidClientActivityTypeException extends DomainException {

  private static final String MESSAGE_INVALID = "The client activity type '%s' is not valid.";

  private InvalidClientActivityTypeException(final String message) {
    super(message);
  }

  public static InvalidClientActivityTypeException becauseValueIsInvalid(final String type) {
    return new InvalidClientActivityTypeException(String.format(MESSAGE_INVALID, type));
  }
}

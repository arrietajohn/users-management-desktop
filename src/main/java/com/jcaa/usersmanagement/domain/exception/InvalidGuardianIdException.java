package com.jcaa.usersmanagement.domain.exception;

public final class InvalidGuardianIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The guardian id must not be empty.";
  private static final String MESSAGE_TOO_LONG = "The guardian id must not exceed %d characters.";

  private InvalidGuardianIdException(final String message) {
    super(message);
  }

  public static InvalidGuardianIdException becauseValueIsEmpty() { return new InvalidGuardianIdException(MESSAGE_EMPTY); }

  public static InvalidGuardianIdException becauseLengthExceedsMaximum(final int maximumLength) {
    return new InvalidGuardianIdException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}

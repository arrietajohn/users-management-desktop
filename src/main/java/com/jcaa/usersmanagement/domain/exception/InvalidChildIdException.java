package com.jcaa.usersmanagement.domain.exception;

public final class InvalidChildIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The child id must not be empty.";
  private static final String MESSAGE_TOO_LONG = "The child id must not exceed %d characters.";

  private InvalidChildIdException(final String message) {
    super(message);
  }

  public static InvalidChildIdException becauseValueIsEmpty() {
    return new InvalidChildIdException(MESSAGE_EMPTY);
  }

  public static InvalidChildIdException becauseLengthExceedsMaximum(final int maximumLength) {
    return new InvalidChildIdException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}

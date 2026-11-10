package com.jcaa.usersmanagement.domain.exception;

public final class InvalidChildNameException extends DomainException {

  private static final String MESSAGE_EMPTY = "The child %s must not be empty.";
  private static final String MESSAGE_TOO_LONG =
      "The child %s must not exceed %d characters.";

  private InvalidChildNameException(final String message) {
    super(message);
  }

  public static InvalidChildNameException becauseFieldIsEmpty(final String field) {
    return new InvalidChildNameException(String.format(MESSAGE_EMPTY, field));
  }

  public static InvalidChildNameException becauseLengthExceedsMaximum(
      final String field, final int maximumLength) {
    return new InvalidChildNameException(String.format(MESSAGE_TOO_LONG, field, maximumLength));
  }
}

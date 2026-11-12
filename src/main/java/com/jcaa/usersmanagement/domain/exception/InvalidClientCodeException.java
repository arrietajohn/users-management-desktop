package com.jcaa.usersmanagement.domain.exception;

public final class InvalidClientCodeException extends DomainException {

  private static final String MESSAGE_EMPTY = "The client code must not be empty.";
  private static final String MESSAGE_TOO_LONG =
      "The client code must have at most %d characters.";

  private InvalidClientCodeException(final String message) {
    super(message);
  }

  public static InvalidClientCodeException becauseValueIsEmpty() {
    return new InvalidClientCodeException(MESSAGE_EMPTY);
  }

  public static InvalidClientCodeException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidClientCodeException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}

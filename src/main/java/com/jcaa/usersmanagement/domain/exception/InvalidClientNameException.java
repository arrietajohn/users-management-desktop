package com.jcaa.usersmanagement.domain.exception;

public final class InvalidClientNameException extends DomainException {

  private static final String MESSAGE_EMPTY = "The client name must not be empty.";
  private static final String MESSAGE_TOO_SHORT =
      "The client name must have at least %d characters.";

  private InvalidClientNameException(final String message) {
    super(message);
  }

  public static InvalidClientNameException becauseValueIsEmpty() {
    return new InvalidClientNameException(MESSAGE_EMPTY);
  }

  public static InvalidClientNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidClientNameException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }
}

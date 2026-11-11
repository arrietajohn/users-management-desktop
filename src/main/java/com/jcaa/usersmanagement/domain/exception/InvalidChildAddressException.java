package com.jcaa.usersmanagement.domain.exception;

public final class InvalidChildAddressException extends DomainException {

  private static final String MESSAGE_EMPTY = "The child address must not be empty.";
  private static final String MESSAGE_TOO_LONG =
      "The child address must not exceed %d characters.";

  private InvalidChildAddressException(final String message) {
    super(message);
  }

  public static InvalidChildAddressException becauseValueIsEmpty() {
    return new InvalidChildAddressException(MESSAGE_EMPTY);
  }

  public static InvalidChildAddressException becauseLengthExceedsMaximum(
      final int maximumLength) {
    return new InvalidChildAddressException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}

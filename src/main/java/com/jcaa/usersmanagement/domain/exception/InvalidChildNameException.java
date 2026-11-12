
package com.jcaa.usersmanagement.domain.exception;

public final class InvalidChildNameException extends DomainException {

  private static final String MESSAGE_EMPTY =
      "The child name must not be empty.";

  private static final String MESSAGE_TOO_SHORT =
      "The child name must have at least %d characters.";

  private InvalidChildNameException(final String message) {
    super(message);
  }

  public static InvalidChildNameException becauseValueIsEmpty() {
    return new InvalidChildNameException(MESSAGE_EMPTY);
  }

  public static InvalidChildNameException becauseLengthIsTooShort(
      final int minimumLength) {
    return new InvalidChildNameException(
        String.format(MESSAGE_TOO_SHORT, minimumLength));
  }
}

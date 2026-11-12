
package com.jcaa.usersmanagement.domain.exception;

public final class InvalidChildIdException extends DomainException {

  private static final String MESSAGE_EMPTY =
      "The child id must not be empty.";

  private InvalidChildIdException(final String message) {
    super(message);
  }

  public static InvalidChildIdException becauseValueIsEmpty() {
    return new InvalidChildIdException(MESSAGE_EMPTY);
  }
}

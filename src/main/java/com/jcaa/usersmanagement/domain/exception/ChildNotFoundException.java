
package com.jcaa.usersmanagement.domain.exception;

public final class ChildNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID =
      "The child with id '%s' was not found.";

  private ChildNotFoundException(final String message) {
    super(message);
  }

  public static ChildNotFoundException becauseIdWasNotFound(final String childId) {
    return new ChildNotFoundException(String.format(MESSAGE_BY_ID, childId));
  }
}

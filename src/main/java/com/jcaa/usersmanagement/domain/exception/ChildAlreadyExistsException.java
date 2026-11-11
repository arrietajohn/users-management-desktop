package com.jcaa.usersmanagement.domain.exception;

public final class ChildAlreadyExistsException extends DomainException {

  private static final String MESSAGE_ID_EXISTS = "A child with id '%s' already exists.";

  private ChildAlreadyExistsException(final String message) {
    super(message);
  }

  public static ChildAlreadyExistsException becauseIdAlreadyExists(final String childId) {
    return new ChildAlreadyExistsException(String.format(MESSAGE_ID_EXISTS, childId));
  }
}

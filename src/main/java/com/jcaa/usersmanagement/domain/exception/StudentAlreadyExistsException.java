package com.jcaa.usersmanagement.domain.exception;

public final class StudentAlreadyExistsException extends DomainException {

  private static final String MESSAGE_ID_EXISTS = "A student with id '%s' already exists.";

  private StudentAlreadyExistsException(final String message) {
    super(message);
  }

  public static StudentAlreadyExistsException becauseIdAlreadyExists(final String studentId) {
    return new StudentAlreadyExistsException(String.format(MESSAGE_ID_EXISTS, studentId));
  }
}

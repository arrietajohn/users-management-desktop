package com.jcaa.usersmanagement.domain.exception;

public final class StudentNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID = "The student with id '%s' was not found.";

  private StudentNotFoundException(final String message) {
    super(message);
  }

  public static StudentNotFoundException becauseIdWasNotFound(final String studentId) {
    return new StudentNotFoundException(String.format(MESSAGE_BY_ID, studentId));
  }
}

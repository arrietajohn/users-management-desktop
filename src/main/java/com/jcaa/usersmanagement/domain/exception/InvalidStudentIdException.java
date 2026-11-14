package com.jcaa.usersmanagement.domain.exception;

public final class InvalidStudentIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The student id must not be empty.";

  private InvalidStudentIdException(final String message) {
    super(message);
  }

  public static InvalidStudentIdException becauseValueIsEmpty() {
    return new InvalidStudentIdException(MESSAGE_EMPTY);
  }
}
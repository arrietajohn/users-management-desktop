package com.jcaa.usersmanagement.domain.exception;

public final class InvalidStudentGroupException extends DomainException {

  private static final String MESSAGE_EMPTY = "The student group must not be empty.";

  private InvalidStudentGroupException(final String message) {
    super(message);
  }

  public static InvalidStudentGroupException becauseValueIsEmpty() {
    return new InvalidStudentGroupException(MESSAGE_EMPTY);
  }
}

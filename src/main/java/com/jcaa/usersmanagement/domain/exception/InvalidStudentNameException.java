package com.jcaa.usersmanagement.domain.exception;

public final class InvalidStudentNameException extends DomainException {

  private static final String MESSAGE_EMPTY = "The student name must not be empty.";
  private static final String MESSAGE_TOO_SHORT =
      "The student name must have at least %d characters.";

  private InvalidStudentNameException(final String message) {
    super(message);
  }

  public static InvalidStudentNameException becauseValueIsEmpty() {
    return new InvalidStudentNameException(MESSAGE_EMPTY);
  }

  public static InvalidStudentNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidStudentNameException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }
}

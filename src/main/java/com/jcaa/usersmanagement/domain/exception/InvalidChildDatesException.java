package com.jcaa.usersmanagement.domain.exception;

public final class InvalidChildDatesException extends DomainException {

  private static final String MESSAGE_BIRTH_DATE_NULL = "The child birth date must not be null.";
  private static final String MESSAGE_ADMISSION_DATE_NULL =
          "The child admission date must not be null.";
  private static final String MESSAGE_BIRTH_NOT_BEFORE_ADMISSION =
          "The child birth date must be before the admission date.";
  private static final String MESSAGE_DISCHARGE_BEFORE_ADMISSION =
          "The child discharge date must not be before the admission date.";
  private static final String MESSAGE_DATE_FORMAT_INVALID =
          "The child %s '%s' is not a valid date. Use the format yyyy-MM-dd.";

  private InvalidChildDatesException(final String message) {
    super(message);
  }

  public static InvalidChildDatesException becauseBirthDateIsNull() {
    return new InvalidChildDatesException(MESSAGE_BIRTH_DATE_NULL);
  }

  public static InvalidChildDatesException becauseAdmissionDateIsNull() {
    return new InvalidChildDatesException(MESSAGE_ADMISSION_DATE_NULL);
  }

  public static InvalidChildDatesException becauseBirthDateIsNotBeforeAdmissionDate() {
    return new InvalidChildDatesException(MESSAGE_BIRTH_NOT_BEFORE_ADMISSION);
  }

  public static InvalidChildDatesException becauseDischargeDateIsBeforeAdmissionDate() {
    return new InvalidChildDatesException(MESSAGE_DISCHARGE_BEFORE_ADMISSION);
  }

  public static InvalidChildDatesException becauseDateFormatIsInvalid(
          final String field, final String value) {
    return new InvalidChildDatesException(String.format(MESSAGE_DATE_FORMAT_INVALID, field, value));
  }
}

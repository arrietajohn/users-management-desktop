package com.jcaa.usersmanagement.domain.exception;

public final class InvalidClientPhoneException extends DomainException {

  private static final String MESSAGE_EMPTY = "The client phone must not be empty.";
  private static final String MESSAGE_FORMAT = "The client phone '%s' has an invalid format.";

  private InvalidClientPhoneException(final String message) {
    super(message);
  }

  public static InvalidClientPhoneException becauseValueIsEmpty() {
    return new InvalidClientPhoneException(MESSAGE_EMPTY);
  }

  public static InvalidClientPhoneException becauseFormatIsInvalid(final String phone) {
    return new InvalidClientPhoneException(String.format(MESSAGE_FORMAT, phone));
  }
}

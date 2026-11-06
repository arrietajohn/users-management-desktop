package com.jcaa.usersmanagement.domain.exception;

public final class InvalidPersonaException extends DomainException {

  private InvalidPersonaException(final String message) {
    super(message);
  }

  public static InvalidPersonaException becauseIdIsInvalid() {
    return new InvalidPersonaException("The persona id must be greater than zero.");
  }

  public static InvalidPersonaException becauseRequiredFieldIsEmpty(
      final String field) {
    return new InvalidPersonaException(
        String.format("The field %s must not be empty.", field));
  }

  public static InvalidPersonaException becauseMunicipalityIdIsInvalid() {
    return new InvalidPersonaException(
        "The municipality id must be greater than zero.");
  }

  public static InvalidPersonaException becauseBirthDateIsInvalid() {
    return new InvalidPersonaException(
        "The birth date cannot be in the future.");
  }

  public static InvalidPersonaException becauseAgeIsInvalid() {
    return new InvalidPersonaException("The age cannot be negative.");
  }

  public static InvalidPersonaException becauseHeightIsInvalid() {
    return new InvalidPersonaException("The height must be greater than zero.");
  }
  public static InvalidPersonaException becauseNameIsTooShort() {
  return new InvalidPersonaException(
      "The persona name must contain at least 3 characters.");
}
public static InvalidPersonaException becauseLastNameIsTooShort() {
  return new InvalidPersonaException(
      "The persona last name must contain at least 3 characters.");
}
}
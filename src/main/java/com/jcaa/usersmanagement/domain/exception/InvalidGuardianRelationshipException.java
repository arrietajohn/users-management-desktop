package com.jcaa.usersmanagement.domain.exception;

public final class InvalidGuardianRelationshipException extends DomainException {

  private static final String MESSAGE_INVALID =
      "The guardian relationship '%s' is not valid.";

  private InvalidGuardianRelationshipException(final String message) {
    super(message);
  }

  public static InvalidGuardianRelationshipException becauseValueIsInvalid(
      final String relationship) {
    return new InvalidGuardianRelationshipException(String.format(MESSAGE_INVALID, relationship));
  }
}

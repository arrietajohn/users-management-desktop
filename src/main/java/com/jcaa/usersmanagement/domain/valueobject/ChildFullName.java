package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidChildNameException;

public record ChildFullName(
    String firstName, String secondName, String firstSurname, String secondSurname) {

  private static final int MAXIMUM_LENGTH = 50;

  public ChildFullName {
    firstName = normalizeRequired(firstName, "first name");
    secondName = normalizeOptional(secondName, "second name");
    firstSurname = normalizeRequired(firstSurname, "first surname");
    secondSurname = normalizeOptional(secondSurname, "second surname");
  }

  private static String normalizeRequired(final String value, final String field) {
    if (value == null || value.trim().isEmpty()) {
      throw InvalidChildNameException.becauseFieldIsEmpty(field);
    }
    return normalize(value.trim(), field);
  }

  private static String normalizeOptional(final String value, final String field) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return normalize(value.trim(), field);
  }

  private static String normalize(final String value, final String field) {
    if (value.length() > MAXIMUM_LENGTH) {
      throw InvalidChildNameException.becauseLengthExceedsMaximum(field, MAXIMUM_LENGTH);
    }
    return value;
  }
}

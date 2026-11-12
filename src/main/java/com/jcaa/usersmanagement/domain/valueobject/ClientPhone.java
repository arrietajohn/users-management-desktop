package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidClientPhoneException;
import java.util.Objects;
import java.util.regex.Pattern;

public record ClientPhone(String value) {

  // Digitos, espacios, guiones y parentesis, con un signo + inicial opcional (7 a 20 caracteres).
  private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9 ()-]{7,20}$");

  public ClientPhone {
    final String normalizedValue =
        Objects.requireNonNull(value, "ClientPhone cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidClientPhoneException.becauseValueIsEmpty();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!PHONE_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidClientPhoneException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}

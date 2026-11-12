
package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidChildIdException;
import java.util.Objects;

public record ChildId(String value) {

  public ChildId {
    final String normalizedValue =
        Objects.requireNonNull(value, "ChildId cannot be null").trim();

    validateNotEmpty(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidChildIdException.becauseValueIsEmpty();
    }
  }

  @Override
  public String toString() {
    return value;
  }
}


package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidChildNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChildNameTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(strings = {"Juan Carlos", "   Juan Carlos   ", "Juan Carlos \t"})
  @DisplayName("Should create ChildName with trimmed value")
  void shouldCreateChildNameWithTrimmedValue(final String input) {
    // Arrange
    final String correctChildName = "Juan Carlos";
    // Act
    final ChildName childName = new ChildName(input);
    // Assert
    assertEquals(correctChildName, childName.toString());
  }

  // --- Exception flow ---

  @Test
  @DisplayName("Should throw NullPointerException when ChildName is null")
  void shouldThrowNullPointerExceptionWhenChildNameIsNull() {
    assertThrows(NullPointerException.class, () -> new ChildName(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  ", "\t", "\n", "\r", "\f", "\b", "Jo", "Al"})
  @DisplayName("Should throw InvalidChildNameException when ChildName is empty or too short")
  void shouldThrowInvalidChildNameExceptionWhenChildNameIsInvalid(final String input) {
    assertThrows(InvalidChildNameException.class, () -> new ChildName(input));
  }
}

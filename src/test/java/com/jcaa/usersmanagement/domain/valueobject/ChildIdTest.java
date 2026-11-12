
package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidChildIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChildIdTest {

  // --- Happy path ---

  @ParameterizedTest
  @ValueSource(strings = {" child123 ", "  child123  ", "child123\t"})
  @DisplayName("Should create ChildId with trimmed value")
  void shouldCreateChildIdWithTrimmedValue(final String input) {
    // Arrange
    final String correctChildId = "child123";
    // Act
    final ChildId childId = new ChildId(input);
    // Assert
    assertEquals(correctChildId, childId.toString());
  }

  // --- Exception flow ---

  @Test
  @DisplayName("Should throw NullPointerException when ChildId is null")
  void shouldThrowNullPointerExceptionWhenChildIdIsNull() {
    assertThrows(NullPointerException.class, () -> new ChildId(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  @DisplayName("Should throw InvalidChildIdException when ChildId is empty")
  void shouldThrowInvalidChildIdExceptionWhenChildIdIsEmpty(final String input) {
    assertThrows(InvalidChildIdException.class, () -> new ChildId(input));
  }
}

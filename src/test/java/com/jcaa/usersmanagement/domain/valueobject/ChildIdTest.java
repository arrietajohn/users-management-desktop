package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidChildIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChildIdTest {

  @Test
  @DisplayName("Should preserve a valid value")
  void shouldPreserveValidValue() {
    final ChildId childId = new ChildId("A123");

    assertEquals("A123", childId.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {" A123 ", "  A123  "})
  @DisplayName("Should trim spaces around the value")
  void shouldTrimSpacesAroundValue(final String input) {
    final ChildId childId = new ChildId(input);

    assertEquals("A123", childId.value());
  }

  @Test
  @DisplayName("Should accept a value with exactly 20 characters")
  void shouldAcceptValueWithExactlyTwentyCharacters() {
    final String value = "12345678901234567890";

    assertEquals(value, new ChildId(value).value());
  }

  @Test
  @DisplayName("Should reject a value with 21 characters")
  void shouldRejectValueWithTwentyOneCharacters() {
    final InvalidChildIdException exception =
        assertThrows(
            InvalidChildIdException.class, () -> new ChildId("123456789012345678901"));

    assertTrue(exception.getMessage().contains("20"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  @DisplayName("Should reject an empty value")
  void shouldRejectEmptyValue(final String value) {
    assertThrows(InvalidChildIdException.class, () -> new ChildId(value));
  }

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new ChildId(null));
  }

  @Test
  @DisplayName("Should compare values after normalization")
  void shouldCompareValuesAfterNormalization() {
    assertEquals(new ChildId(" A1 "), new ChildId("A1"));
  }

  @Test
  @DisplayName("Should return the value from toString")
  void shouldReturnValueFromToString() {
    assertEquals("A123", new ChildId("A123").toString());
  }
}

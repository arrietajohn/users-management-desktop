package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidGuardianIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuardianIdTest {

  @Test
  @DisplayName("Should preserve a valid value")
  void shouldPreserveValidValue() {
    final GuardianId guardianId = new GuardianId("G123");

    assertEquals("G123", guardianId.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {" G123 ", "  G123  "})
  @DisplayName("Should trim spaces around the value")
  void shouldTrimSpacesAroundValue(final String input) {
    final GuardianId guardianId = new GuardianId(input);

    assertEquals("G123", guardianId.value());
  }

  @Test
  @DisplayName("Should accept a value with exactly 20 characters")
  void shouldAcceptValueWithExactlyTwentyCharacters() {
    final String value = "12345678901234567890";

    assertEquals(value, new GuardianId(value).value());
  }

  @Test
  @DisplayName("Should reject a value with 21 characters")
  void shouldRejectValueWithTwentyOneCharacters() {
    final InvalidGuardianIdException exception =
        assertThrows(
            InvalidGuardianIdException.class, () -> new GuardianId("123456789012345678901"));

    assertTrue(exception.getMessage().contains("20"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  @DisplayName("Should reject an empty value")
  void shouldRejectEmptyValue(final String value) {
    assertThrows(InvalidGuardianIdException.class, () -> new GuardianId(value));
  }

  @Test
  @DisplayName("Should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> new GuardianId(null));
  }

  @Test
  @DisplayName("Should compare values after normalization")
  void shouldCompareValuesAfterNormalization() {
    assertEquals(new GuardianId(" G1 "), new GuardianId("G1"));
  }

  @Test
  @DisplayName("Should return the value from toString")
  void shouldReturnValueFromToString() {
    assertEquals("G123", new GuardianId("G123").toString());
  }
}

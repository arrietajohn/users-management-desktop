package com.jcaa.usersmanagement.domain.enums;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidGuardianRelationshipException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuardianRelationshipTest {

  @Test
  @DisplayName("Should return the matching relationship for valid input")
  void shouldReturnMatchingRelationshipForValidInput() {
    assertEquals(GuardianRelationship.FAMILIAR, GuardianRelationship.fromString("FAMILIAR"));
    assertEquals(GuardianRelationship.CONOCIDO, GuardianRelationship.fromString("CONOCIDO"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"familiar", "Conocido"})
  @DisplayName("Should ignore case when converting input")
  void shouldIgnoreCaseWhenConvertingInput(final String input) {
    final GuardianRelationship relationship = GuardianRelationship.fromString(input);

    assertEquals(GuardianRelationship.valueOf(input.toUpperCase()), relationship);
  }

  @Test
  @DisplayName("Should reject an unsupported relationship and include its value")
  void shouldRejectUnsupportedRelationshipAndIncludeItsValue() {
    final InvalidGuardianRelationshipException exception =
        assertThrows(
            InvalidGuardianRelationshipException.class,
            () -> GuardianRelationship.fromString("PADRE"));

    assertTrue(exception.getMessage().contains("PADRE"));
  }

  @Test
  @DisplayName("Should reject an empty relationship")
  void shouldRejectEmptyRelationship() {
    assertThrows(
        InvalidGuardianRelationshipException.class, () -> GuardianRelationship.fromString(""));
  }

  @Test
  @DisplayName("Should reject a null relationship")
  void shouldRejectNullRelationship() {
    assertThrows(
        InvalidGuardianRelationshipException.class, () -> GuardianRelationship.fromString(null));
  }

  @Test
  @DisplayName("Should contain exactly two relationships")
  void shouldContainExactlyTwoRelationships() {
    assertArrayEquals(
        new GuardianRelationship[] {GuardianRelationship.FAMILIAR, GuardianRelationship.CONOCIDO},
        GuardianRelationship.values());
  }
}

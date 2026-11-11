package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidChildNameException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChildFullNameTest {

  @Test
  @DisplayName("Should preserve all valid name components")
  void shouldPreserveAllValidNameComponents() {
    final ChildFullName fullName = new ChildFullName("Ana", "Maria", "Lopez", "Diaz");

    assertEquals("Ana", fullName.firstName());
    assertEquals("Maria", fullName.secondName());
    assertEquals("Lopez", fullName.firstSurname());
    assertEquals("Diaz", fullName.secondSurname());
  }

  @Test
  @DisplayName("Should allow only the required name components")
  void shouldAllowOnlyRequiredNameComponents() {
    final ChildFullName fullName = new ChildFullName("Ana", null, "Lopez", null);

    assertNull(fullName.secondName());
    assertNull(fullName.secondSurname());
  }

  @Test
  @DisplayName("Should treat blank optional components as absent")
  void shouldTreatBlankOptionalComponentsAsAbsent() {
    final ChildFullName fullName = new ChildFullName("Ana", "   ", "Lopez", " ");

    assertNull(fullName.secondName());
    assertNull(fullName.secondSurname());
  }

  @Test
  @DisplayName("Should trim spaces around all name components")
  void shouldTrimSpacesAroundAllNameComponents() {
    final ChildFullName fullName =
        new ChildFullName(" Ana ", " Maria ", " Lopez ", " Diaz ");

    assertEquals("Ana", fullName.firstName());
    assertEquals("Maria", fullName.secondName());
    assertEquals("Lopez", fullName.firstSurname());
    assertEquals("Diaz", fullName.secondSurname());
  }

  @Test
  @DisplayName("Should accept 50 characters in every name component")
  void shouldAcceptFiftyCharactersInEveryNameComponent() {
    final String value = "A".repeat(50);

    final ChildFullName fullName = new ChildFullName(value, value, value, value);

    assertEquals(value, fullName.firstName());
    assertEquals(value, fullName.secondName());
    assertEquals(value, fullName.firstSurname());
    assertEquals(value, fullName.secondSurname());
  }

  @ParameterizedTest
  @MethodSource("nameComponentsExceedingMaximumLength")
  @DisplayName("Should reject 51 characters and identify the name component")
  void shouldRejectFiftyOneCharactersAndIdentifyComponent(
      final String field,
      final String firstName,
      final String secondName,
      final String firstSurname,
      final String secondSurname) {
    final InvalidChildNameException exception =
        assertThrows(
            InvalidChildNameException.class,
            () -> new ChildFullName(firstName, secondName, firstSurname, secondSurname));

    assertTrue(exception.getMessage().contains(field));
    assertTrue(exception.getMessage().contains("50"));
  }

  private static Stream<Arguments> nameComponentsExceedingMaximumLength() {
    final String tooLong = "A".repeat(51);
    return Stream.of(
        Arguments.of("first name", tooLong, "Name", "Surname", "Surname"),
        Arguments.of("second name", "Name", tooLong, "Surname", "Surname"),
        Arguments.of("first surname", "Name", "Name", tooLong, "Surname"),
        Arguments.of("second surname", "Name", "Name", "Surname", tooLong));
  }

  @ParameterizedTest
  @MethodSource("invalidRequiredNameComponents")
  @DisplayName("Should reject a missing required component and identify it")
  void shouldRejectMissingRequiredComponentAndIdentifyIt(
      final String field,
      final String firstName,
      final String firstSurname) {
    final InvalidChildNameException exception =
        assertThrows(
            InvalidChildNameException.class,
            () -> new ChildFullName(firstName, null, firstSurname, null));

    assertTrue(exception.getMessage().contains(field));
  }

  private static Stream<Arguments> invalidRequiredNameComponents() {
    return Stream.of(
        Arguments.of("first name", null, "Surname"),
        Arguments.of("first name", "", "Surname"),
        Arguments.of("first name", "   ", "Surname"),
        Arguments.of("first surname", "Name", null),
        Arguments.of("first surname", "Name", ""),
        Arguments.of("first surname", "Name", "   "));
  }

  @Test
  @DisplayName("Should compare equivalent name components by value")
  void shouldCompareEquivalentNameComponentsByValue() {
    assertEquals(
        new ChildFullName(" Ana ", "Maria", "Lopez", null),
        new ChildFullName("Ana", " Maria ", " Lopez ", " "));
  }
}

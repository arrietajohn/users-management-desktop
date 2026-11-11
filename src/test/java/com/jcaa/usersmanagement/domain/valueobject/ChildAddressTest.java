package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidChildAddressException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChildAddressTest {

  @Test
  @DisplayName("Should preserve a valid address")
  void shouldPreserveValidAddress() {
    final ChildAddress address = new ChildAddress("Example Street 1");

    assertEquals("Example Street 1", address.value());
  }

  @Test
  @DisplayName("Should trim spaces around the address")
  void shouldTrimSpacesAroundAddress() {
    final ChildAddress address = new ChildAddress("  Example Street 1  ");

    assertEquals("Example Street 1", address.value());
  }

  @Test
  @DisplayName("Should accept an address with exactly 150 characters")
  void shouldAcceptAddressWithExactlyOneHundredFiftyCharacters() {
    final String value = "A".repeat(150);

    assertEquals(value, new ChildAddress(value).value());
  }

  @Test
  @DisplayName("Should reject an address with 151 characters")
  void shouldRejectAddressWithOneHundredFiftyOneCharacters() {
    final InvalidChildAddressException exception =
        assertThrows(
            InvalidChildAddressException.class, () -> new ChildAddress("A".repeat(151)));

    assertTrue(exception.getMessage().contains("150"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  @DisplayName("Should reject an empty address")
  void shouldRejectEmptyAddress(final String value) {
    assertThrows(InvalidChildAddressException.class, () -> new ChildAddress(value));
  }

  @Test
  @DisplayName("Should throw NullPointerException when address is null")
  void shouldThrowNullPointerExceptionWhenAddressIsNull() {
    assertThrows(NullPointerException.class, () -> new ChildAddress(null));
  }

  @Test
  @DisplayName("Should compare addresses by value")
  void shouldCompareAddressesByValue() {
    assertEquals(new ChildAddress(" Example Street "), new ChildAddress("Example Street"));
  }
}

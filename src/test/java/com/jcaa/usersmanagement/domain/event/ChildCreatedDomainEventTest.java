package com.jcaa.usersmanagement.domain.event;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildAddress;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChildCreatedDomainEventTest {

  private static final String ID = "CH-001";
  private static final LocalDate ADMISSION_DATE = LocalDate.of(2024, 2, 20);

  @Test
  @DisplayName("Should have event name child.created")
  void shouldHaveEventNameChildCreated() {
    final ChildCreatedDomainEvent event = new ChildCreatedDomainEvent(validChild());

    assertEquals("child.created", event.getEventName());
  }

  @Test
  @DisplayName("Should record a non-null occurrence time")
  void shouldRecordNonNullOccurrenceTime() {
    final ChildCreatedDomainEvent event = new ChildCreatedDomainEvent(validChild());

    assertNotNull(event.getOccurredOn());
  }

  @Test
  @DisplayName("Should retain the child instance")
  void shouldRetainChildInstance() {
    final ChildModel child = validChild();
    final ChildCreatedDomainEvent event = new ChildCreatedDomainEvent(child);

    assertSame(child, event.getChild());
  }

  @Test
  @DisplayName("Should return payload with the expected child values")
  void shouldReturnPayloadWithExpectedChildValues() {
    final ChildCreatedDomainEvent event = new ChildCreatedDomainEvent(validChildWithAllNameParts());

    final Map<String, String> payload = event.payload();

    assertAll(
        () -> assertEquals(5, payload.size()),
        () -> assertEquals(ID, payload.get("id")),
        () -> assertEquals("First Second Surname Other", payload.get("fullName")),
        () -> assertEquals(ADMISSION_DATE.toString(), payload.get("admissionDate")),
        () -> assertEquals("G-001", payload.get("guardianId")),
        () -> assertEquals("FAMILIAR", payload.get("guardianRelationship")));
  }

  @Test
  @DisplayName("Should build payload without absent optional child values")
  void shouldBuildPayloadWithoutAbsentOptionalValues() {
    final ChildCreatedDomainEvent event = new ChildCreatedDomainEvent(validChild());

    final Map<String, String> payload = assertDoesNotThrow(event::payload);

    assertAll(
        () -> assertEquals(5, payload.size()),
        () -> assertEquals("First Surname", payload.get("fullName")),
        () -> assertTrue(payload.values().stream().noneMatch(value -> value == null)));
  }

  private static ChildModel validChild() {
    return ChildModel.create(
        new ChildId(ID),
        new ChildFullName("First", null, "Surname", null),
        LocalDate.of(2020, 1, 10),
        ADMISSION_DATE,
        null,
        new GuardianId("G-001"),
        GuardianRelationship.FAMILIAR);
  }

  private static ChildModel validChildWithAllNameParts() {
    return ChildModel.create(
        new ChildId(ID),
        new ChildFullName("First", "Second", "Surname", "Other"),
        LocalDate.of(2020, 1, 10),
        ADMISSION_DATE,
        new ChildAddress("Example Street 1"),
        new GuardianId("G-001"),
        GuardianRelationship.FAMILIAR);
  }
}

package com.jcaa.usersmanagement.domain.event;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChildUpdatedDomainEventTest {

  private static final String ID = "CH-002";
  private static final LocalDate ADMISSION_DATE = LocalDate.of(2024, 2, 20);

  @Test
  @DisplayName("Should have event name child.updated")
  void shouldHaveEventNameChildUpdated() {
    final ChildUpdatedDomainEvent event = new ChildUpdatedDomainEvent(validChild());

    assertEquals("child.updated", event.getEventName());
  }

  @Test
  @DisplayName("Should record a non-null occurrence time")
  void shouldRecordNonNullOccurrenceTime() {
    final ChildUpdatedDomainEvent event = new ChildUpdatedDomainEvent(validChild());

    assertNotNull(event.getOccurredOn());
  }

  @Test
  @DisplayName("Should retain the child instance")
  void shouldRetainChildInstance() {
    final ChildModel child = validChild();
    final ChildUpdatedDomainEvent event = new ChildUpdatedDomainEvent(child);

    assertSame(child, event.getChild());
  }

  @Test
  @DisplayName("Should return payload with the expected child values")
  void shouldReturnPayloadWithExpectedChildValues() {
    final ChildUpdatedDomainEvent event = new ChildUpdatedDomainEvent(validChildWithAllNameParts());

    final Map<String, String> payload = event.payload();

    assertAll(
        () -> assertEquals(5, payload.size()),
        () -> assertEquals(ID, payload.get("id")),
        () -> assertEquals("First Second Surname Other", payload.get("fullName")),
        () -> assertEquals(ADMISSION_DATE.toString(), payload.get("admissionDate")),
        () -> assertEquals("G-002", payload.get("guardianId")),
        () -> assertEquals("CONOCIDO", payload.get("guardianRelationship")));
  }

  @Test
  @DisplayName("Should build payload without absent optional child values")
  void shouldBuildPayloadWithoutAbsentOptionalValues() {
    final ChildUpdatedDomainEvent event = new ChildUpdatedDomainEvent(validChild());

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
        new GuardianId("G-002"),
        GuardianRelationship.CONOCIDO);
  }

  private static ChildModel validChildWithAllNameParts() {
    return ChildModel.create(
        new ChildId(ID),
        new ChildFullName("First", "Second", "Surname", "Other"),
        LocalDate.of(2020, 1, 10),
        ADMISSION_DATE,
        null,
        new GuardianId("G-002"),
        GuardianRelationship.CONOCIDO);
  }
}

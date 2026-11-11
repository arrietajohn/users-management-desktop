package com.jcaa.usersmanagement.domain.event;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChildDeletedDomainEventTest {

  private static final String ID = "CH-003";

  @Test
  @DisplayName("Should have event name child.deleted")
  void shouldHaveEventNameChildDeleted() {
    final ChildDeletedDomainEvent event = new ChildDeletedDomainEvent(new ChildId(ID));

    assertEquals("child.deleted", event.getEventName());
  }

  @Test
  @DisplayName("Should record a non-null occurrence time")
  void shouldRecordNonNullOccurrenceTime() {
    final ChildDeletedDomainEvent event = new ChildDeletedDomainEvent(new ChildId(ID));

    assertNotNull(event.getOccurredOn());
  }

  @Test
  @DisplayName("Should retain the child id instance")
  void shouldRetainChildIdInstance() {
    final ChildId childId = new ChildId(ID);
    final ChildDeletedDomainEvent event = new ChildDeletedDomainEvent(childId);

    assertSame(childId, event.getChildId());
  }

  @Test
  @DisplayName("Should return a payload containing only the child id")
  void shouldReturnPayloadContainingOnlyChildId() {
    final ChildDeletedDomainEvent event = new ChildDeletedDomainEvent(new ChildId(ID));

    final Map<String, String> payload = event.payload();

    assertAll(
        () -> assertEquals(1, payload.size()),
        () -> assertEquals(ID, payload.get("id")),
        () -> assertEquals(Map.of("id", ID), payload));
  }
}

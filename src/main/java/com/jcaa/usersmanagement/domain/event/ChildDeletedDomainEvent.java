package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import java.util.Map;
import lombok.Getter;

@Getter
public final class ChildDeletedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "child.deleted";

  private final ChildId childId;

  public ChildDeletedDomainEvent(final ChildId childId) {
    super(EVENT_NAME);
    this.childId = childId;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of("id", childId.value());
  }
}

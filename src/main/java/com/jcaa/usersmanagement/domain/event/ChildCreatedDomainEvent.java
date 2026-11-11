package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.model.ChildModel;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public final class ChildCreatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "child.created";

  private final ChildModel child;

  public ChildCreatedDomainEvent(final ChildModel child) {
    super(EVENT_NAME);
    this.child = child;
  }

  @Override
  public Map<String, String> payload() {
    // El payload omite los campos opcionales para evitar valores nulos en el mapa.
    final String fullName =
        Stream.of(
                child.getFullName().firstName(),
                child.getFullName().secondName(),
                child.getFullName().firstSurname(),
                child.getFullName().secondSurname())
            .filter(value -> value != null && !value.isEmpty())
            .collect(Collectors.joining(" "));
    return Map.of(
        "id", child.getId().value(),
        "fullName", fullName,
        "admissionDate", child.getAdmissionDate().toString(),
        "guardianId", child.getGuardianId().value(),
        "guardianRelationship", child.getGuardianRelationship().name());
  }
}

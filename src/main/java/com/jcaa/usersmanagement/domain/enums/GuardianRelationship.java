package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidGuardianRelationshipException;

public enum GuardianRelationship {
  FAMILIAR,
  CONOCIDO;

  public static GuardianRelationship fromString(final String value) {
    for (final GuardianRelationship relationship : values()) {
      if (relationship.name().equalsIgnoreCase(value)) {
        return relationship;
      }
    }
    throw InvalidGuardianRelationshipException.becauseValueIsInvalid(value);
  }
}

package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.PersonaId;

public interface DeletePersonaPort {
  void delete(PersonaId personaId);
}

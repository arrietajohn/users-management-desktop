package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.PersonaModel;

public interface SavePersonaPort {
  PersonaModel save(PersonaModel persona);
}

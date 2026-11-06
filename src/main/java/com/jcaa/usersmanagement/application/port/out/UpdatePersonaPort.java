package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.PersonaModel;

public interface UpdatePersonaPort {
  PersonaModel update(PersonaModel persona);
}

package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import java.util.Optional;

public interface GetPersonaByIdPort {
  Optional<PersonaModel> getById(PersonaId personaId);
}

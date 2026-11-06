package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetPersonaByIdQuery;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetPersonaByIdUseCase {
  PersonaModel execute(@NotNull @Valid GetPersonaByIdQuery query);
}

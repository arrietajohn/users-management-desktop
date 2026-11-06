package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreatePersonaCommand;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreatePersonaUseCase {
  PersonaModel execute(@NotNull @Valid CreatePersonaCommand command);
}

package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeletePersonaCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeletePersonaUseCase {
  void execute(@NotNull @Valid DeletePersonaCommand command);
}

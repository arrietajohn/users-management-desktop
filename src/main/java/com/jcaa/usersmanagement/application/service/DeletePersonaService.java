package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeletePersonaUseCase;
import com.jcaa.usersmanagement.application.port.out.DeletePersonaPort;
import com.jcaa.usersmanagement.application.port.out.GetPersonaByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeletePersonaCommand;
import com.jcaa.usersmanagement.application.service.mapper.PersonaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.PersonaNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeletePersonaService implements DeletePersonaUseCase {

  private final DeletePersonaPort deletePersonaPort;
  private final GetPersonaByIdPort getPersonaByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeletePersonaCommand command) {
    validateCommand(command);

    final PersonaId personaId =
        PersonaApplicationMapper.fromDeleteCommandToPersonaId(command);

    ensurePersonaExists(personaId);
    deletePersonaPort.delete(personaId);
  }

  private void validateCommand(final DeletePersonaCommand command) {
    final Set<ConstraintViolation<DeletePersonaCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensurePersonaExists(final PersonaId personaId) {
    getPersonaByIdPort
        .getById(personaId)
        .orElseThrow(
            () -> PersonaNotFoundException.becauseIdWasNotFound(personaId.value()));
  }
}

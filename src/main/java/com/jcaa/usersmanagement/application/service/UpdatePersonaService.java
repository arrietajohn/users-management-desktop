package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdatePersonaUseCase;
import com.jcaa.usersmanagement.application.port.out.GetPersonaByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdatePersonaPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdatePersonaCommand;
import com.jcaa.usersmanagement.application.service.mapper.PersonaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.PersonaNotFoundException;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdatePersonaService implements UpdatePersonaUseCase {

  private final UpdatePersonaPort updatePersonaPort;
  private final GetPersonaByIdPort getPersonaByIdPort;
  private final Validator validator;

  @Override
  public PersonaModel execute(final UpdatePersonaCommand command) {
    validateCommand(command);

    final PersonaId personaId = new PersonaId(command.idPersona());
    findExistingPersonaOrFail(personaId);

    final PersonaModel personaToUpdate =
        PersonaApplicationMapper.fromUpdateCommandToModel(command);

    return updatePersonaPort.update(personaToUpdate);
  }

  private void validateCommand(final UpdatePersonaCommand command) {
    final Set<ConstraintViolation<UpdatePersonaCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private PersonaModel findExistingPersonaOrFail(final PersonaId personaId) {
    return getPersonaByIdPort
        .getById(personaId)
        .orElseThrow(
            () -> PersonaNotFoundException.becauseIdWasNotFound(personaId.value()));
  }
}

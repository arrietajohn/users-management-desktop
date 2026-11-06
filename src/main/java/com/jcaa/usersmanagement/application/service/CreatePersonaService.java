package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreatePersonaUseCase;
import com.jcaa.usersmanagement.application.port.out.GetPersonaByIdPort;
import com.jcaa.usersmanagement.application.port.out.SavePersonaPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreatePersonaCommand;
import com.jcaa.usersmanagement.application.service.mapper.PersonaApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.PersonaAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreatePersonaService implements CreatePersonaUseCase {

  private final SavePersonaPort savePersonaPort;
  private final GetPersonaByIdPort getPersonaByIdPort;
  private final Validator validator;

  @Override
  public PersonaModel execute(final CreatePersonaCommand command) {
    validateCommand(command);

    final PersonaId personaId = new PersonaId(command.idPersona());
    ensurePersonaDoesNotExist(personaId);

    final PersonaModel personaToSave =
        PersonaApplicationMapper.fromCreateCommandToModel(command);

    return savePersonaPort.save(personaToSave);
  }

  private void validateCommand(final CreatePersonaCommand command) {
    final Set<ConstraintViolation<CreatePersonaCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensurePersonaDoesNotExist(final PersonaId personaId) {
    getPersonaByIdPort
        .getById(personaId)
        .ifPresent(
            ignored -> {
              throw PersonaAlreadyExistsException.becauseIdAlreadyExists(
                  personaId.value());
            });
  }
}

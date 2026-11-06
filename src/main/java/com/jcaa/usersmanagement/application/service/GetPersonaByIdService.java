package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetPersonaByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetPersonaByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetPersonaByIdQuery;
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
public final class GetPersonaByIdService implements GetPersonaByIdUseCase {

  private final GetPersonaByIdPort getPersonaByIdPort;
  private final Validator validator;

  @Override
  public PersonaModel execute(final GetPersonaByIdQuery query) {
    validateQuery(query);

    final PersonaId personaId =
        PersonaApplicationMapper.fromGetPersonaByIdQueryToPersonaId(query);

    return getPersonaByIdPort
        .getById(personaId)
        .orElseThrow(
            () -> PersonaNotFoundException.becauseIdWasNotFound(personaId.value()));
  }

  private void validateQuery(final GetPersonaByIdQuery query) {
    final Set<ConstraintViolation<GetPersonaByIdQuery>> violations =
        validator.validate(query);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}

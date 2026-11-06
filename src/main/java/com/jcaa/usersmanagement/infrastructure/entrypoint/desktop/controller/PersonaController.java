package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreatePersonaUseCase;
import com.jcaa.usersmanagement.application.port.in.DeletePersonaUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllPersonasUseCase;
import com.jcaa.usersmanagement.application.port.in.GetPersonaByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdatePersonaUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreatePersonaRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.PersonaResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdatePersonaRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.PersonaDesktopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class PersonaController {

  private final CreatePersonaUseCase createPersonaUseCase;
  private final UpdatePersonaUseCase updatePersonaUseCase;
  private final DeletePersonaUseCase deletePersonaUseCase;
  private final GetPersonaByIdUseCase getPersonaByIdUseCase;
  private final GetAllPersonasUseCase getAllPersonasUseCase;

  public PersonaResponse createPersona(final CreatePersonaRequest request) {
    final var command = PersonaDesktopMapper.toCreateCommand(request);
    final var persona = createPersonaUseCase.execute(command);
    return PersonaDesktopMapper.toResponse(persona);
  }

  public PersonaResponse updatePersona(final UpdatePersonaRequest request) {
    final var command = PersonaDesktopMapper.toUpdateCommand(request);
    final var persona = updatePersonaUseCase.execute(command);
    return PersonaDesktopMapper.toResponse(persona);
  }

  public PersonaResponse findPersonaById(final Integer id) {
    final var query = PersonaDesktopMapper.toGetByIdQuery(id);
    final var persona = getPersonaByIdUseCase.execute(query);
    return PersonaDesktopMapper.toResponse(persona);
  }

  public List<PersonaResponse> listAllPersonas() {
    final var personas = getAllPersonasUseCase.execute();
    return PersonaDesktopMapper.toResponseList(personas);
  }

  public void deletePersona(final Integer id) {
    final var command = PersonaDesktopMapper.toDeleteCommand(id);
    deletePersonaUseCase.execute(command);
  }
}
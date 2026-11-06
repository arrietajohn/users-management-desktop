package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeletePersonaPort;
import com.jcaa.usersmanagement.application.port.out.GetAllPersonasPort;
import com.jcaa.usersmanagement.application.port.out.GetPersonaByIdPort;
import com.jcaa.usersmanagement.application.port.out.SavePersonaPort;
import com.jcaa.usersmanagement.application.port.out.UpdatePersonaPort;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class PersonaRepositoryInMemory
    implements SavePersonaPort,
        UpdatePersonaPort,
        GetPersonaByIdPort,
        GetAllPersonasPort,
        DeletePersonaPort {

  private final ConcurrentMap<PersonaId, PersonaModel> personas =
      new ConcurrentHashMap<>();

  @Override
  public PersonaModel save(final PersonaModel persona) {
    personas.put(persona.getIdPersona(), persona);
    return persona;
  }

  @Override
  public PersonaModel update(final PersonaModel persona) {
    personas.put(persona.getIdPersona(), persona);
    return persona;
  }

  @Override
  public Optional<PersonaModel> getById(final PersonaId personaId) {
    return Optional.ofNullable(personas.get(personaId));
  }

  @Override
  public List<PersonaModel> getAll() {
    return personas.values().stream()
        .sorted(Comparator.comparing(persona -> persona.getIdPersona().value()))
        .toList();
  }

  @Override
  public void delete(final PersonaId personaId) {
    personas.remove(personaId);
  }
}

package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreatePersonaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeletePersonaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdatePersonaCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetPersonaByIdQuery;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreatePersonaRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.PersonaResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdatePersonaRequest;
import java.util.List;

public final class PersonaDesktopMapper {

  private PersonaDesktopMapper() {}

  public static CreatePersonaCommand toCreateCommand(
      final CreatePersonaRequest request) {
    return new CreatePersonaCommand(
        request.idPersona(),
        request.nombres(),
        request.apellidos(),
        request.fechaNacimiento(),
        request.idMunicipioNacimiento(),
        request.edad(),
        request.estatura(),
        request.sexo(),
        request.nivelEstudios(),
        request.situacionMilitar(),
        request.dni(),
        request.estadoCivil(),
        request.ocupacion(),
        request.grupoEtnico());
  }

  public static UpdatePersonaCommand toUpdateCommand(
      final UpdatePersonaRequest request) {
    return new UpdatePersonaCommand(
        request.idPersona(),
        request.nombres(),
        request.apellidos(),
        request.fechaNacimiento(),
        request.idMunicipioNacimiento(),
        request.edad(),
        request.estatura(),
        request.sexo(),
        request.nivelEstudios(),
        request.situacionMilitar(),
        request.dni(),
        request.estadoCivil(),
        request.ocupacion(),
        request.grupoEtnico());
  }

  public static GetPersonaByIdQuery toGetByIdQuery(final Integer id) {
    return new GetPersonaByIdQuery(id);
  }

  public static DeletePersonaCommand toDeleteCommand(final Integer id) {
    return new DeletePersonaCommand(id);
  }

  public static PersonaResponse toResponse(final PersonaModel persona) {
    return new PersonaResponse(
        persona.getIdPersona().value(),
        persona.getNombres().value(),
        persona.getApellidos().value(),
        persona.getFechaNacimiento().value(),
        persona.getIdMunicipioNacimiento().value(),
        persona.getEdad(),
        persona.getEstatura(),
        persona.getSexo(),
        persona.getNivelEstudios(),
        persona.getSituacionMilitar(),
        persona.getDni(),
        persona.getEstadoCivil(),
        persona.getOcupacion(),
        persona.getGrupoEtnico());
  }

  public static List<PersonaResponse> toResponseList(
      final List<PersonaModel> personas) {
    return personas.stream().map(PersonaDesktopMapper::toResponse).toList();
  }
}
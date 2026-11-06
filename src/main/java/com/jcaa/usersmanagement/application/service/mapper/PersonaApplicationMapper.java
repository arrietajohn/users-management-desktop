package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreatePersonaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeletePersonaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdatePersonaCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetPersonaByIdQuery;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.FechaNacimiento;
import com.jcaa.usersmanagement.domain.valueobject.MunicipioId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaApellido;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaNombre;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonaApplicationMapper {

  public PersonaModel fromCreateCommandToModel(final CreatePersonaCommand command) {
    return new PersonaModel(
        new PersonaId(command.idPersona()),
        new PersonaNombre(command.nombres()),
        new PersonaApellido(command.apellidos()),
        new FechaNacimiento(command.fechaNacimiento()),
        new MunicipioId(command.idMunicipioNacimiento()),
        command.edad(),
        command.estatura(),
        command.sexo(),
        command.nivelEstudios(),
        command.situacionMilitar(),
        command.dni(),
        command.estadoCivil(),
        command.ocupacion(),
        command.grupoEtnico());
  }

  public PersonaModel fromUpdateCommandToModel(final UpdatePersonaCommand command) {
    return new PersonaModel(
        new PersonaId(command.idPersona()),
        new PersonaNombre(command.nombres()),
        new PersonaApellido(command.apellidos()),
        new FechaNacimiento(command.fechaNacimiento()),
        new MunicipioId(command.idMunicipioNacimiento()),
        command.edad(),
        command.estatura(),
        command.sexo(),
        command.nivelEstudios(),
        command.situacionMilitar(),
        command.dni(),
        command.estadoCivil(),
        command.ocupacion(),
        command.grupoEtnico());
  }

  public PersonaId fromGetPersonaByIdQueryToPersonaId(
      final GetPersonaByIdQuery query) {
    return new PersonaId(query.id());
  }

  public PersonaId fromDeleteCommandToPersonaId(final DeletePersonaCommand command) {
    return new PersonaId(command.id());
  }
}

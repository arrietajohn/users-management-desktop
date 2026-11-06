package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.PersonaModel;
import com.jcaa.usersmanagement.domain.valueobject.FechaNacimiento;
import com.jcaa.usersmanagement.domain.valueobject.MunicipioId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaApellido;
import com.jcaa.usersmanagement.domain.valueobject.PersonaId;
import com.jcaa.usersmanagement.domain.valueobject.PersonaNombre;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.PersonaPersistenceDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonaPersistenceMapper {

  public PersonaPersistenceDto fromModelToDto(final PersonaModel persona) {
    return new PersonaPersistenceDto(
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

  public PersonaModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return new PersonaModel(
        new PersonaId(resultSet.getInt("id_persona")),
        new PersonaNombre(resultSet.getString("nombres")),
        new PersonaApellido(resultSet.getString("apellidos")),
        new FechaNacimiento(resultSet.getDate("fecha_nacimiento").toLocalDate()),
        new MunicipioId(resultSet.getInt("id_municipio_nacimiento")),
        resultSet.getInt("edad"),
        resultSet.getBigDecimal("estatura"),
        resultSet.getString("sexo"),
        resultSet.getString("nivel_estudios"),
        resultSet.getString("situacion_militar"),
        resultSet.getString("dni"),
        resultSet.getString("estado_civil"),
        resultSet.getString("ocupacion"),
        resultSet.getString("grupo_etnico"));
  }

  public List<PersonaModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<PersonaModel> personas = new ArrayList<>();
    while (resultSet.next()) {
      personas.add(fromResultSetToModel(resultSet));
    }
    return personas;
  }
}
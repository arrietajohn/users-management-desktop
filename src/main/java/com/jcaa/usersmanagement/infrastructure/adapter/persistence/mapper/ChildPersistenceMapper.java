package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildAddress;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ChildPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ChildEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChildPersistenceMapper {

  public ChildPersistenceDto fromModelToDto(final ChildModel child) {
    final ChildFullName fullName = child.getFullName();
    return new ChildPersistenceDto(
        child.getId().value(),
        fullName.firstName(),
        fullName.secondName(),
        fullName.firstSurname(),
        fullName.secondSurname(),
        child.getBirthDate().toString(),
        child.getAdmissionDate().toString(),
        child.getDischargeDate() == null ? null : child.getDischargeDate().toString(),
        child.getAddress() == null ? null : child.getAddress().value(),
        child.getGuardianId().value(),
        child.getGuardianRelationship().name());
  }

  public ChildEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new ChildEntity(
        resultSet.getString("matricula"),
        resultSet.getString("primer_nombre"),
        resultSet.getString("segundo_nombre"),
        resultSet.getString("primer_apellido"),
        resultSet.getString("segundo_apellido"),
        resultSet.getString("fecha_nacimiento"),
        resultSet.getString("fecha_ingreso"),
        resultSet.getString("fecha_baja"),
        resultSet.getString("direccion"),
        resultSet.getString("ci_responsable"),
        resultSet.getString("relacion_responsable"));
  }

  public ChildModel fromEntityToModel(final ChildEntity entity) {
    final ChildFullName fullName =
        new ChildFullName(
            entity.firstName(),
            entity.secondName(),
            entity.firstSurname(),
            entity.secondSurname());
    final LocalDate dischargeDate =
        entity.dischargeDate() == null ? null : LocalDate.parse(entity.dischargeDate());
    final ChildAddress address =
        entity.address() == null ? null : new ChildAddress(entity.address());
    return new ChildModel(
        new ChildId(entity.id()),
        fullName,
        LocalDate.parse(entity.birthDate()),
        LocalDate.parse(entity.admissionDate()),
        dischargeDate,
        address,
        new GuardianId(entity.guardianId()),
        GuardianRelationship.fromString(entity.guardianRelationship()));
  }

  public ChildModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<ChildModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
    final List<ChildModel> children = new ArrayList<>();
    while (resultSet.next()) {
      children.add(fromResultSetToModel(resultSet));
    }
    return children;
  }
}

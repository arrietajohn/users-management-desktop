package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.enums.GuardianRelationship;
import com.jcaa.usersmanagement.domain.exception.InvalidChildDatesException;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildAddress;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import com.jcaa.usersmanagement.domain.valueobject.GuardianId;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChildApplicationMapper {

  public ChildModel fromCreateCommandToModel(final CreateChildCommand command) {
    return ChildModel.create(
        new ChildId(command.id()),
        new ChildFullName(
            command.firstName(),
            command.secondName(),
            command.firstSurname(),
            command.secondSurname()),
        parseDate("birth date", command.birthDate()),
        parseDate("admission date", command.admissionDate()),
        toAddress(command.address()),
        new GuardianId(command.guardianId()),
        GuardianRelationship.fromString(command.guardianRelationship()));
  }

  public ChildId fromGetChildByIdQueryToChildId(final GetChildByIdQuery query) {
    return new ChildId(query.id());
  }

  public ChildModel fromUpdateCommandToModel(final UpdateChildCommand command) {
    return new ChildModel(
        new ChildId(command.id()),
        new ChildFullName(
            command.firstName(),
            command.secondName(),
            command.firstSurname(),
            command.secondSurname()),
        parseDate("birth date", command.birthDate()),
        parseDate("admission date", command.admissionDate()),
        parseOptionalDate("discharge date", command.dischargeDate()),
        toAddress(command.address()),
        new GuardianId(command.guardianId()),
        GuardianRelationship.fromString(command.guardianRelationship()));
  }

  public ChildId fromDeleteCommandToChildId(final DeleteChildCommand command) {
    return new ChildId(command.id());
  }

  private LocalDate parseDate(final String field, final String value) {
    try {
      return LocalDate.parse(value);
    } catch (final DateTimeParseException exception) {
      throw InvalidChildDatesException.becauseDateFormatIsInvalid(field, value);
    }
  }

  private LocalDate parseOptionalDate(final String field, final String value) {
    if (Objects.isNull(value) || value.isBlank()) {
      return null;
    }
    return parseDate(field, value);
  }

  private ChildAddress toAddress(final String value) {
    if (Objects.isNull(value) || value.isBlank()) {
      return null;
    }
    return new ChildAddress(value);
  }
}

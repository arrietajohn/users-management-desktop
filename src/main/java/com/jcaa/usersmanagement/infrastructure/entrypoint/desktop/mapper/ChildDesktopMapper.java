package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteChildCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildFullName;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateChildRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateChildRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ChildDesktopMapper {

  private static final String STATUS_ACTIVE = "ACTIVE";
  private static final String STATUS_WITHDRAWN = "WITHDRAWN";

  private ChildDesktopMapper() {}

  public static CreateChildCommand toCreateCommand(final CreateChildRequest request) {
    return new CreateChildCommand(
        request.id(),
        request.firstName(),
        blankToNull(request.secondName()),
        request.firstSurname(),
        blankToNull(request.secondSurname()),
        request.birthDate(),
        request.admissionDate(),
        blankToNull(request.address()),
        request.guardianId(),
        request.guardianRelationship());
  }

  public static UpdateChildCommand toUpdateCommand(final UpdateChildRequest request) {
    return new UpdateChildCommand(
        request.id(),
        request.firstName(),
        blankToNull(request.secondName()),
        request.firstSurname(),
        blankToNull(request.secondSurname()),
        request.birthDate(),
        request.admissionDate(),
        blankToNull(request.dischargeDate()),
        blankToNull(request.address()),
        request.guardianId(),
        request.guardianRelationship());
  }

  public static DeleteChildCommand toDeleteCommand(final String id) {
    return new DeleteChildCommand(id);
  }

  public static GetChildByIdQuery toGetByIdQuery(final String id) {
    return new GetChildByIdQuery(id);
  }

  public static ChildResponse toResponse(final ChildModel child) {
    return new ChildResponse(
        child.getId().value(),
        assembleFullName(child.getFullName()),
        child.getBirthDate().toString(),
        child.getAdmissionDate().toString(),
        Objects.isNull(child.getDischargeDate()) ? null : child.getDischargeDate().toString(),
        Objects.isNull(child.getAddress()) ? null : child.getAddress().value(),
        child.getGuardianId().value(),
        child.getGuardianRelationship().name(),
        child.isActive() ? STATUS_ACTIVE : STATUS_WITHDRAWN);
  }

  public static List<ChildResponse> toResponseList(final List<ChildModel> children) {
    return children.stream().map(ChildDesktopMapper::toResponse).toList();
  }

  private static String assembleFullName(final ChildFullName fullName) {
    return Stream.of(
            fullName.firstName(),
            fullName.secondName(),
            fullName.firstSurname(),
            fullName.secondSurname())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  private static String blankToNull(final String value) {
    return Objects.isNull(value) || value.isBlank() ? null : value;
  }
}

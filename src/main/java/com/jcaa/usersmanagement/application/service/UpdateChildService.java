package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateChildUseCase;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateChildPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateChildCommand;
import com.jcaa.usersmanagement.application.service.mapper.ChildApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateChildService implements UpdateChildUseCase {

  private final UpdateChildPort updateChildPort;
  private final GetChildByIdPort getChildByIdPort;
  private final Validator validator;

  @Override
  public ChildModel execute(final UpdateChildCommand command) {
    validateCommand(command);

    final ChildId childId = new ChildId(command.id());
    ensureChildExists(childId);

    final ChildModel childToUpdate = ChildApplicationMapper.fromUpdateCommandToModel(command);
    return updateChildPort.update(childToUpdate);
  }

  private void validateCommand(final UpdateChildCommand command) {
    final Set<ConstraintViolation<UpdateChildCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureChildExists(final ChildId childId) {
    getChildByIdPort
        .getById(childId)
        .orElseThrow(() -> ChildNotFoundException.becauseIdWasNotFound(childId.value()));
  }
}

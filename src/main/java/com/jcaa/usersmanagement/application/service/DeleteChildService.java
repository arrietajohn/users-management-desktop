package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteChildUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteChildPort;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteChildCommand;
import com.jcaa.usersmanagement.application.service.mapper.ChildApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteChildService implements DeleteChildUseCase {

  private final DeleteChildPort deleteChildPort;
  private final GetChildByIdPort getChildByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteChildCommand command) {
    validateCommand(command);

    final ChildId childId = ChildApplicationMapper.fromDeleteCommandToChildId(command);
    ensureChildExists(childId);
    deleteChildPort.delete(childId);
  }

  private void validateCommand(final DeleteChildCommand command) {
    final Set<ConstraintViolation<DeleteChildCommand>> violations = validator.validate(command);
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

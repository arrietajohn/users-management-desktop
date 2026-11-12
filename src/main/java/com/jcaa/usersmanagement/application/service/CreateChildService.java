package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateChildUseCase;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveChildPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateChildCommand;
import com.jcaa.usersmanagement.application.service.mapper.ChildApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ChildAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import com.jcaa.usersmanagement.domain.valueobject.ChildId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateChildService implements CreateChildUseCase {

  private final SaveChildPort saveChildPort;
  private final GetChildByIdPort getChildByIdPort;
  private final Validator validator;

  @Override
  public ChildModel execute(final CreateChildCommand command) {
    validateCommand(command);

    final ChildId childId = new ChildId(command.id());
    ensureIdIsNotTaken(childId);

    final ChildModel childToSave = ChildApplicationMapper.fromCreateCommandToModel(command);
    return saveChildPort.save(childToSave);
  }

  private void validateCommand(final CreateChildCommand command) {
    final Set<ConstraintViolation<CreateChildCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureIdIsNotTaken(final ChildId childId) {
    getChildByIdPort
        .getById(childId)
        .ifPresent(
            ignored -> {
              throw ChildAlreadyExistsException.becauseIdAlreadyExists(childId.value());
            });
  }
}

package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetChildByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetChildByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
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
public final class GetChildByIdService implements GetChildByIdUseCase {

  private final GetChildByIdPort getChildByIdPort;
  private final Validator validator;

  @Override
  public ChildModel execute(final GetChildByIdQuery query) {
    validateQuery(query);

    final ChildId childId = ChildApplicationMapper.fromGetChildByIdQueryToChildId(query);
    return getChildByIdPort
        .getById(childId)
        .orElseThrow(() -> ChildNotFoundException.becauseIdWasNotFound(childId.value()));
  }

  private void validateQuery(final GetChildByIdQuery query) {
    final Set<ConstraintViolation<GetChildByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}

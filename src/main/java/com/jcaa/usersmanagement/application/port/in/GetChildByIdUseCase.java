package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetChildByIdQuery;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetChildByIdUseCase {

  ChildModel execute(@NotNull @Valid GetChildByIdQuery query);
}

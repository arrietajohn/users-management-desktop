package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateChildUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteChildUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllChildrenUseCase;
import com.jcaa.usersmanagement.application.port.in.GetChildByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateChildUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateChildRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateChildRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.ChildDesktopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ChildController {

  private final CreateChildUseCase createChildUseCase;
  private final UpdateChildUseCase updateChildUseCase;
  private final DeleteChildUseCase deleteChildUseCase;
  private final GetChildByIdUseCase getChildByIdUseCase;
  private final GetAllChildrenUseCase getAllChildrenUseCase;

  public List<ChildResponse> listAllChildren() {
    final var children = getAllChildrenUseCase.execute();
    return ChildDesktopMapper.toResponseList(children);
  }

  public ChildResponse findChildById(final String id) {
    final var query = ChildDesktopMapper.toGetByIdQuery(id);
    final var child = getChildByIdUseCase.execute(query);
    return ChildDesktopMapper.toResponse(child);
  }

  public ChildResponse createChild(final CreateChildRequest request) {
    final var command = ChildDesktopMapper.toCreateCommand(request);
    final var child = createChildUseCase.execute(command);
    return ChildDesktopMapper.toResponse(child);
  }

  public ChildResponse updateChild(final UpdateChildRequest request) {
    final var command = ChildDesktopMapper.toUpdateCommand(request);
    final var child = updateChildUseCase.execute(command);
    return ChildDesktopMapper.toResponse(child);
  }

  public void deleteChild(final String id) {
    final var command = ChildDesktopMapper.toDeleteCommand(id);
    deleteChildUseCase.execute(command);
  }
}

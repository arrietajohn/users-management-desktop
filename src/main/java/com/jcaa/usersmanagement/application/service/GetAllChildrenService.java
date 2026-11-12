package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllChildrenUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllChildrenPort;
import com.jcaa.usersmanagement.domain.model.ChildModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAllChildrenService implements GetAllChildrenUseCase {

  private final GetAllChildrenPort getAllChildrenPort;

  @Override
  public List<ChildModel> execute() {
    return getAllChildrenPort.getAll();
  }
}

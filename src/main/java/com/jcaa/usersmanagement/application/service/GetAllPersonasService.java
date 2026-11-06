package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllPersonasUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllPersonasPort;
import com.jcaa.usersmanagement.domain.model.PersonaModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAllPersonasService implements GetAllPersonasUseCase {

  private final GetAllPersonasPort getAllPersonasPort;

  @Override
  public List<PersonaModel> execute() {
    return getAllPersonasPort.getAll();
  }
}

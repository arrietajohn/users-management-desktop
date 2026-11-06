package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.PersonaModel;
import java.util.List;

public interface GetAllPersonasUseCase {
  List<PersonaModel> execute();
}

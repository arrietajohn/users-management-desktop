package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.PersonaModel;
import java.util.List;

public interface GetAllPersonasPort {
  List<PersonaModel> getAll();
}

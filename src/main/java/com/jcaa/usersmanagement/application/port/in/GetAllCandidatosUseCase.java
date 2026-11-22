package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import java.util.List;

public interface GetAllCandidatosUseCase {
    List<CandidatoModel> getAll();
}

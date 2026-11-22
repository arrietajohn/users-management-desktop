package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import java.util.List;

public interface GetAllCandidatosPort {
    List<CandidatoModel> findAll();
}

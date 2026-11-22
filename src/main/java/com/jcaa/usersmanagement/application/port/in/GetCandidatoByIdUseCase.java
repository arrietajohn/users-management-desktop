package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public interface GetCandidatoByIdUseCase {
    CandidatoModel getById(Integer id);
}

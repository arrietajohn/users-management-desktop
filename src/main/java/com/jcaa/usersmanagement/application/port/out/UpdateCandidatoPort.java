package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public interface UpdateCandidatoPort {
    CandidatoModel update(CandidatoModel candidato);
}

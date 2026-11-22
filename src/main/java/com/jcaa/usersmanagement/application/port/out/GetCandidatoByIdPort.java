package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import java.util.Optional;

public interface GetCandidatoByIdPort {
    Optional<CandidatoModel> findById(Integer id);
}

package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCandidatoCommand;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public interface CreateCandidatoUseCase {
    CandidatoModel create(CreateCandidatoCommand command);
}

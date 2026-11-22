package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateCandidatoCommand;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public interface UpdateCandidatoUseCase {
    CandidatoModel update(UpdateCandidatoCommand command);
}

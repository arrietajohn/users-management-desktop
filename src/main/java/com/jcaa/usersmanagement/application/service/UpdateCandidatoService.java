package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateCandidatoUseCase;
import com.jcaa.usersmanagement.application.port.out.GetCandidatoByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateCandidatoPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCandidatoCommand;
import com.jcaa.usersmanagement.application.service.mapper.CandidatoApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.CandidatoNotFoundException;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;


public class UpdateCandidatoService implements UpdateCandidatoUseCase {
    private final GetCandidatoByIdPort getCandidatoByIdPort;
    private final UpdateCandidatoPort updateCandidatoPort;
    private final CandidatoApplicationMapper mapper;

    public UpdateCandidatoService(GetCandidatoByIdPort getCandidatoByIdPort, UpdateCandidatoPort updateCandidatoPort){
        this.getCandidatoByIdPort = getCandidatoByIdPort;
        this.updateCandidatoPort = updateCandidatoPort;
        this.mapper = new CandidatoApplicationMapper();

    }

    @Override
    public CandidatoModel update(UpdateCandidatoCommand command) {
        CandidatoModel candidato= getCandidatoByIdPort.findById(command.id())
                .orElseThrow(() -> new CandidatoNotFoundException(String.valueOf(command.id())));
        mapper.updateDomain(candidato, command);
        return updateCandidatoPort.update(candidato);

    }
}

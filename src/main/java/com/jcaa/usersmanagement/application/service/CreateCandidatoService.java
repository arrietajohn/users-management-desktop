package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateCandidatoUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveCandidatoPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateCandidatoCommand;
import com.jcaa.usersmanagement.application.service.mapper.CandidatoApplicationMapper;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public class CreateCandidatoService implements CreateCandidatoUseCase {
    private final SaveCandidatoPort saveCandidatoPort;
    private final CandidatoApplicationMapper mapper;

    public CreateCandidatoService(SaveCandidatoPort saveCandidatoPort){
        this.saveCandidatoPort = saveCandidatoPort;
        this.mapper = new CandidatoApplicationMapper();

    }

    @Override
    public CandidatoModel create(CreateCandidatoCommand command) {
        CandidatoModel candidato = mapper.toDomain(command);
        return saveCandidatoPort.save(candidato);
    }
}

package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetCandidatoByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetCandidatoByIdPort;
import com.jcaa.usersmanagement.domain.exception.CandidatoNotFoundException;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public class GetCandidatoByIdService implements GetCandidatoByIdUseCase {
    private final GetCandidatoByIdPort getCandidatoByIdPort;

    public GetCandidatoByIdService(GetCandidatoByIdPort getCandidatoByIdPort) {
        this.getCandidatoByIdPort = getCandidatoByIdPort;
    }

    @Override

    public CandidatoModel getById(Integer id){
        return getCandidatoByIdPort.findById(id)
                .orElseThrow(() ->new CandidatoNotFoundException(String.valueOf(id) ));
    }
}
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllCandidatosUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllCandidatosPort;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import java.util.List;

public class GetAllCandidatosService implements GetAllCandidatosUseCase {
    private final GetAllCandidatosPort getAllCandidatosPort;

    public GetAllCandidatosService (GetAllCandidatosPort getAllCandidatosPort){
        this.getAllCandidatosPort = getAllCandidatosPort;
    }

    @Override
    public List<CandidatoModel> getAll(){
        return getAllCandidatosPort.findAll();
    }
}

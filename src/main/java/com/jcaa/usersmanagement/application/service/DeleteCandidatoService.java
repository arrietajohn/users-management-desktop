package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteCandidatoUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteCandidatoPort;
import com.jcaa.usersmanagement.application.port.out.GetCandidatoByIdPort;
import com.jcaa.usersmanagement.domain.exception.CandidatoNotFoundException;

public class DeleteCandidatoService implements  DeleteCandidatoUseCase {
    private final DeleteCandidatoPort deleteCandidatoPort;
    private final GetCandidatoByIdPort getCandidatoByIdPort;

    public DeleteCandidatoService(DeleteCandidatoPort deleteCandidatoPort, GetCandidatoByIdPort getCandidatoByIdPort) {
        this.deleteCandidatoPort = deleteCandidatoPort;
        this.getCandidatoByIdPort = getCandidatoByIdPort;
    }
    @Override
    public void delete(Integer id) {
        getCandidatoByIdPort.findById(id)
                .orElseThrow(() -> new CandidatoNotFoundException(String.valueOf(id)));
        deleteCandidatoPort.deleteById(id);
    }

}

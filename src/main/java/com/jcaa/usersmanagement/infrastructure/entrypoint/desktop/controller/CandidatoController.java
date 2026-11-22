package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.*;
import com.jcaa.usersmanagement.application.service.dto.command.CreateCandidatoCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCandidatoCommand;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;

import java.util.List;

public class CandidatoController {

    private final CreateCandidatoUseCase createCandidatoUseCase;
    private final GetAllCandidatosUseCase getAllCandidatosUseCase;
    private final GetCandidatoByIdUseCase getCandidatoByIdUseCase;
    private final UpdateCandidatoUseCase updateCandidatoUseCase;
    private final DeleteCandidatoUseCase deleteCandidatoUseCase;

    public CandidatoController(
            CreateCandidatoUseCase createCandidatoUseCase,
            GetAllCandidatosUseCase getAllCandidatosUseCase,
            GetCandidatoByIdUseCase getCandidatoByIdUseCase,
            UpdateCandidatoUseCase updateCandidatoUseCase,
            DeleteCandidatoUseCase deleteCandidatoUseCase) {
        this.createCandidatoUseCase = createCandidatoUseCase;
        this.getAllCandidatosUseCase = getAllCandidatosUseCase;
        this.getCandidatoByIdUseCase = getCandidatoByIdUseCase;
        this.updateCandidatoUseCase = updateCandidatoUseCase;
        this.deleteCandidatoUseCase = deleteCandidatoUseCase;
    }

    public CandidatoModel create(CreateCandidatoCommand command) {
        return createCandidatoUseCase.create(command);
    }

    public List<CandidatoModel> getAll() {
        return getAllCandidatosUseCase.getAll();
    }

    public CandidatoModel getById(Integer id) {
        return getCandidatoByIdUseCase.getById(id);
    }

    public CandidatoModel update(UpdateCandidatoCommand command) {
        return updateCandidatoUseCase.update(command);
    }

    public void delete(Integer id) {
        deleteCandidatoUseCase.delete(id);
    }
}
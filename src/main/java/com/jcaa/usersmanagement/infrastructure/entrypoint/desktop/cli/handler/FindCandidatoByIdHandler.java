package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.CandidatoNotFoundException;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CandidatoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CandidatoResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.CandidatoDesktopMapper;

public class FindCandidatoByIdHandler implements OperationHandler {

    private final CandidatoController controller;
    private final ConsoleIO io;
    private final CandidatoDesktopMapper mapper;

    public FindCandidatoByIdHandler(CandidatoController controller, ConsoleIO io) {
        this.controller = controller;
        this.io = io;
        this.mapper = new CandidatoDesktopMapper();
    }

    @Override
    public void handle() {
        try {
            io.println("=== Buscar Candidato por ID ===");
            Integer id = io.readInt("ID: ");
            CandidatoModel candidato = controller.getById(id);
            CandidatoResponse response = mapper.toResponse(candidato);
            io.println("-----------------------------");
            io.println("ID: " + response.id());
            io.println("Nombre: " + response.nombre());
            io.println("Dirección: " + response.direccion());
            io.println("Teléfono: " + response.telefono());
            io.println("Fecha nacimiento: " + response.fechaNacimiento());
            io.println("Tipo: " + response.tipo());
            io.println("Tutor: " + response.nombreTutor());
        } catch (CandidatoNotFoundException e) {
            io.println("Error: " + e.getMessage());
        } catch (Exception e) {
            io.println("Error inesperado: " + e.getMessage());
        }
    }
}
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CandidatoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CandidatoResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.CandidatoDesktopMapper;

import java.util.List;

public class ListCandidatosHandler implements OperationHandler {

    private final CandidatoController controller;
    private final ConsoleIO io;
    private final CandidatoDesktopMapper mapper;

    public ListCandidatosHandler(CandidatoController controller, ConsoleIO io) {
        this.controller = controller;
        this.io = io;
        this.mapper = new CandidatoDesktopMapper();
    }

    @Override
    public void handle() {
        try {
            io.println("=== Lista de Candidatos ===");
            List<CandidatoModel> candidatos = controller.getAll();
            if (candidatos.isEmpty()) {
                io.println("No hay candidatos registrados.");
                return;
            }
            for (CandidatoModel c : candidatos) {
                CandidatoResponse response = mapper.toResponse(c);
                io.println("-----------------------------");
                io.println("ID: " + response.id());
                io.println("Nombre: " + response.nombre());
                io.println("Teléfono: " + response.telefono());
                io.println("Tipo: " + response.tipo());
            }
        } catch (Exception e) {
            io.println("Error inesperado: " + e.getMessage());
        }
    }
}
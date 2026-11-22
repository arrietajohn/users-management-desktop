package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;
import com.jcaa.usersmanagement.domain.exception.CandidatoNotFoundException;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CandidatoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CandidatoResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateCandidatoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.CandidatoDesktopMapper;

public class UpdateCandidatoHandler implements OperationHandler {

    private final CandidatoController controller;
    private final ConsoleIO io;
    private final CandidatoDesktopMapper mapper;

    public UpdateCandidatoHandler(CandidatoController controller, ConsoleIO io) {
        this.controller = controller;
        this.io = io;
        this.mapper = new CandidatoDesktopMapper();
    }

    @Override
    public void handle() {
        try {
            io.println("=== Actualizar Candidato ===");
            Integer id = io.readInt("ID del candidato a actualizar: ");
            String nombre = io.readRequired("Nuevo nombre: ");
            String direccion = io.readRequired("Nueva dirección: ");
            String telefono = io.readRequired("Nuevo teléfono: ");
            String fotografia = io.readOptional("Nueva fotografía (URL): ");
            String tipoStr = io.readRequired("Nuevo tipo (ADULTO/NINO): ");
            CandidatoTipo tipo = CandidatoTipo.valueOf(tipoStr.toUpperCase());
            String nombreTutor = io.readOptional("Nuevo tutor (vacío si no aplica): ");

            UpdateCandidatoRequest request = new UpdateCandidatoRequest(
                    id, nombre, direccion, telefono, fotografia,
                    tipo, nombreTutor.isEmpty() ? null : nombreTutor
            );

            CandidatoModel candidato = controller.update(mapper.toCommand(request));
            CandidatoResponse response = mapper.toResponse(candidato);
            io.println("Candidato actualizado: " + response.nombre());
        } catch (CandidatoNotFoundException e) {
            io.println("Error: " + e.getMessage());
        } catch (Exception e) {
            io.println("Error inesperado: " + e.getMessage());
        }
    }
}
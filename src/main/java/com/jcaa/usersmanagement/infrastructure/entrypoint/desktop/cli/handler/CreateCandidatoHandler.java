package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCandidatoCommand;
import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;
import com.jcaa.usersmanagement.domain.exception.CandidatoNotFoundException;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CandidatoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CandidatoResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateCandidatoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.CandidatoDesktopMapper;

import java.time.LocalDate;

public class CreateCandidatoHandler implements OperationHandler {

    private final CandidatoController controller;
    private final ConsoleIO io;
    private final CandidatoDesktopMapper mapper;

    public CreateCandidatoHandler(CandidatoController controller, ConsoleIO io) {
        this.controller = controller;
        this.io = io;
        this.mapper = new CandidatoDesktopMapper();
    }

    @Override
    public void handle() {
        try {
            io.println("=== Crear Candidato ===");
            Integer id = io.readInt("ID: ");
            String nombre = io.readRequired("Nombre: ");
            String direccion = io.readRequired("Dirección: ");
            String telefono = io.readRequired("Teléfono: ");
            String fechaStr = io.readRequired("Fecha nacimiento (YYYY-MM-DD): ");
            LocalDate fechaNacimiento = LocalDate.parse(fechaStr);
            String fotografia = io.readOptional("Fotografía (URL): ");
            String tipoStr = io.readRequired("Tipo (ADULTO/NINO): ");
            CandidatoTipo tipo = CandidatoTipo.valueOf(tipoStr.toUpperCase());
            String nombreTutor = io.readOptional("Nombre tutor (vacío si no aplica): ");

            CreateCandidatoRequest request = new CreateCandidatoRequest(
                    id, nombre, direccion, telefono, fechaNacimiento,
                    fotografia, tipo, nombreTutor.isEmpty() ? null : nombreTutor
            );

            CandidatoModel candidato = controller.create(mapper.toCommand(request));
            CandidatoResponse response = mapper.toResponse(candidato);
            io.println("Candidato creado exitosamente: " + response.nombre());
        } catch (CandidatoNotFoundException e) {
            io.println("Error: " + e.getMessage());
        } catch (Exception e) {
            io.println("Error inesperado: " + e.getMessage());
        }
    }
}
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCandidatoCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCandidatoCommand;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CandidatoResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateCandidatoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateCandidatoRequest;

public class CandidatoDesktopMapper {

    public CreateCandidatoCommand toCommand(CreateCandidatoRequest request) {
        return new CreateCandidatoCommand(
                request.id(),
                request.nombre(),
                request.direccion(),
                request.telefono(),
                request.fechaNacimiento(),
                request.fotografia(),
                request.tipo(),
                request.nombreTutor()
        );
    }

    public UpdateCandidatoCommand toCommand(UpdateCandidatoRequest request) {
        return new UpdateCandidatoCommand(
                request.id(),
                request.nombre(),
                request.direccion(),
                request.telefono(),
                request.fotografia(),
                request.tipo(),
                request.nombreTutor()
        );
    }

    public CandidatoResponse toResponse(CandidatoModel model) {
        return new CandidatoResponse(
                model.getId(),
                model.getNombre(),
                model.getDireccion(),
                model.getTelefono(),
                model.getFechaNacimiento(),
                model.getFotografia(),
                model.getTipo(),
                model.getNombreTutor()
        );
    }
}
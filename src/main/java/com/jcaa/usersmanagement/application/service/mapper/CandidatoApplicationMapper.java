package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateCandidatoCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateCandidatoCommand;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;

public class CandidatoApplicationMapper {
    public CandidatoModel toDomain(CreateCandidatoCommand command){
        return new CandidatoModel(command.id(), command.nombre(), command.direccion(),
                command.telefono(), command.fotografia(), command.fechaNacimiento(), command.tipo(),
                command.nombreTutor());
    }

    public void updateDomain(CandidatoModel candidato, UpdateCandidatoCommand command){
        candidato.setNombre(command.nombre());
        candidato.setDireccion(command.direccion());
        candidato.setTelefono(command.telefono());
        candidato.setFotografia(command.fotografia());
        candidato.setTipo(command.tipo());
        candidato.setNombreTutor(command.nombreTutor());
    }
}

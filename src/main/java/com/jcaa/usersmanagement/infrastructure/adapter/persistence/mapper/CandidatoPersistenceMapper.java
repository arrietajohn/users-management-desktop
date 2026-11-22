package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;
import com.jcaa.usersmanagement.domain.model.CandidatoModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.CandidatoPersistenceDto;

public class CandidatoPersistenceMapper {
    public CandidatoModel toDomain(CandidatoPersistenceDto dto){
        return new CandidatoModel(
                dto.idCandidato(), dto.nombre(), dto.direccion(), dto.telefono(),
                dto.fotografia(), dto.fechaNacimiento(), CandidatoTipo.valueOf(dto.tipo()), dto.nombreTutor());
    }
    public CandidatoPersistenceDto toDto(CandidatoModel model){
        return new CandidatoPersistenceDto(
                model.getId(), model.getNombre(), model.getDireccion(), model.getTelefono(), model.getFechaNacimiento(),
                model.getFotografia(),model.getTipo().name(), model.getNombreTutor()
        );
    }
}

package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;

public record UpdateCandidatoRequest(
        Integer id,
        String nombre,
        String direccion,
        String telefono,
        String fotografia,
        CandidatoTipo tipo,
        String nombreTutor) {

}
package com.jcaa.usersmanagement.application.service.dto.command;

import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;

public record UpdateCandidatoCommand(
        Integer id, String nombre, String direccion, String telefono, String fotografia, CandidatoTipo tipo,
        String nombreTutor
) {
}

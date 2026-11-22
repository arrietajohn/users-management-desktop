package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;
import java.time.LocalDate;

public record CreateCandidatoRequest(
        Integer id,
        String nombre,
        String direccion,
        String telefono,
        LocalDate fechaNacimiento,
        String fotografia,
        CandidatoTipo tipo,
        String nombreTutor) {

}
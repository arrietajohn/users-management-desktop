package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

import java.time.LocalDate;

public record CandidatoPersistenceDto(
        Integer idCandidato, String nombre, String direccion, String telefono, LocalDate fechaNacimiento,
        String fotografia, String tipo, String nombreTutor
) {
}

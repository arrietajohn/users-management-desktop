package com.jcaa.usersmanagement.application.service.dto.command;

import com.jcaa.usersmanagement.domain.enums.CandidatoTipo;
import java.time.LocalDate;

public record CreateCandidatoCommand(
        Integer id, String nombre, String direccion, String telefono,
        LocalDate fechaNacimiento, String fotografia, CandidatoTipo tipo, String nombreTutor
) {
}

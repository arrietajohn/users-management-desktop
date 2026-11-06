package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.PersonaNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.PersonaResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.PersonaController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.PersonaResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdatePersonaRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdatePersonaHandler implements OperationHandler {

  private final PersonaController personaController;
  private final ConsoleIO console;
  private final PersonaResponsePrinter printer;

  @Override
  public void handle() {
    final Integer id = console.readInt("ID de persona: ");
    final String nombres = console.readRequired("Nuevos nombres: ");
    final String apellidos = console.readRequired("Nuevos apellidos: ");
    final LocalDate fechaNacimiento =
        LocalDate.parse(console.readRequired("Fecha de nacimiento (AAAA-MM-DD): "));
    final Integer municipio = console.readInt("ID municipio de nacimiento: ");
    final Integer edad = console.readInt("Edad: ");
    final String estaturaInput = console.readOptional("Estatura (opcional): ");
    final BigDecimal estatura =
        estaturaInput.isBlank() ? null : new BigDecimal(estaturaInput);

    final UpdatePersonaRequest request =
        new UpdatePersonaRequest(
            id,
            nombres,
            apellidos,
            fechaNacimiento,
            municipio,
            edad,
            estatura,
            console.readOptional("Sexo (opcional): "),
            console.readOptional("Nivel de estudios (opcional): "),
            console.readOptional("Situacion militar (opcional): "),
            console.readOptional("DNI (opcional): "),
            console.readOptional("Estado civil (opcional): "),
            console.readOptional("Ocupacion (opcional): "),
            console.readOptional("Grupo etnico (opcional): "));

    try {
      final PersonaResponse updated = personaController.updatePersona(request);
      console.println("\n  Persona actualizada correctamente.");
      printer.print(updated);
    } catch (final PersonaNotFoundException exception) {
      console.println("  No encontrada: " + exception.getMessage());
    }
  }
}

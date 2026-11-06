package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.PersonaAlreadyExistsException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.PersonaResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.PersonaController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreatePersonaRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.PersonaResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreatePersonaHandler implements OperationHandler {

  private final PersonaController personaController;
  private final ConsoleIO console;
  private final PersonaResponsePrinter printer;

  @Override
  public void handle() {
    final Integer id = console.readInt("ID de persona: ");
    final String nombres = console.readRequired("Nombres: ");
    final String apellidos = console.readRequired("Apellidos: ");
    final LocalDate fechaNacimiento =
        LocalDate.parse(console.readRequired("Fecha de nacimiento (AAAA-MM-DD): "));
    final Integer municipio = console.readInt("ID municipio de nacimiento: ");
    final Integer edad = console.readInt("Edad: ");
    final String estaturaInput = console.readOptional("Estatura (opcional): ");
    final BigDecimal estatura =
        estaturaInput.isBlank() ? null : new BigDecimal(estaturaInput);

    final CreatePersonaRequest request =
        new CreatePersonaRequest(
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
      final PersonaResponse created = personaController.createPersona(request);
      console.println("\n  Persona registrada correctamente.");
      printer.print(created);
    } catch (final PersonaAlreadyExistsException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}

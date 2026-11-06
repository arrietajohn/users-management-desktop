package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.PersonaNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.PersonaResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.PersonaController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindPersonaByIdHandler implements OperationHandler {

  private final PersonaController personaController;
  private final ConsoleIO console;
  private final PersonaResponsePrinter printer;

  @Override
  public void handle() {
    final Integer id = console.readInt("ID de persona: ");
    try {
      printer.print(personaController.findPersonaById(id));
    } catch (final PersonaNotFoundException exception) {
      console.println("  No encontrada: " + exception.getMessage());
    }
  }
}

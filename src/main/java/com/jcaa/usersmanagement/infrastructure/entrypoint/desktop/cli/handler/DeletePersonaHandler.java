package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.PersonaNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.PersonaController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeletePersonaHandler implements OperationHandler {

  private final PersonaController personaController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final Integer id = console.readInt("ID de persona que desea eliminar: ");
    try {
      personaController.deletePersona(id);
      console.println("  Persona eliminada correctamente.");
    } catch (final PersonaNotFoundException exception) {
      console.println("  No encontrada: " + exception.getMessage());
    }
  }
}

package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.PersonaResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.PersonaController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListPersonasHandler implements OperationHandler {

  private final PersonaController personaController;
  private final ConsoleIO console;
  private final PersonaResponsePrinter printer;

  @Override
  public void handle() {
    printer.printList(personaController.listAllPersonas());
  }
}

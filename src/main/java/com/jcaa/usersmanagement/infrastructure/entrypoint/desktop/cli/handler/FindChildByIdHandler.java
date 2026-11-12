package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ChildResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindChildByIdHandler implements OperationHandler {

  private final ChildController childController;
  private final ConsoleIO console;
  private final ChildResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Child ID: ");

    try {
      final ChildResponse child = childController.findChildById(id);
      printer.print(child);
    } catch (final ChildNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}

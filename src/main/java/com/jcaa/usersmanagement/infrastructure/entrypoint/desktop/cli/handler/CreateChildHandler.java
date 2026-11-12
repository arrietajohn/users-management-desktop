package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ChildAlreadyExistsException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ChildResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateChildRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateChildHandler implements OperationHandler {

  private final ChildController childController;
  private final ConsoleIO console;
  private final ChildResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Child ID (matricula)                 : ");
    final String firstName = console.readRequired("First name                           : ");
    final String secondName = console.readOptional("Second name (optional)               : ");
    final String firstSurname = console.readRequired("First surname                        : ");
    final String secondSurname = console.readOptional("Second surname (optional)            : ");
    final String birthDate = console.readRequired("Birth date (yyyy-MM-dd)              : ");
    final String admissionDate = console.readRequired("Admission date (yyyy-MM-dd)          : ");
    final String address = console.readOptional("Address (optional)                   : ");
    final String guardianId = console.readRequired("Guardian ID (CI)                     : ");
    final String relationship = console.readRequired("Relationship (FAMILIAR / CONOCIDO)  : ");

    try {
      final ChildResponse created =
          childController.createChild(
              new CreateChildRequest(
                  id,
                  firstName,
                  secondName,
                  firstSurname,
                  secondSurname,
                  birthDate,
                  admissionDate,
                  address,
                  guardianId,
                  relationship));
      console.println("\n  Child created successfully.");
      printer.print(created);
    } catch (final ChildAlreadyExistsException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}

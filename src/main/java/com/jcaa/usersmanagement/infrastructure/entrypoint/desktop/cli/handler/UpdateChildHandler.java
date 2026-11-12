package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ChildNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ChildResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ChildController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateChildRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateChildHandler implements OperationHandler {

  private final ChildController childController;
  private final ConsoleIO console;
  private final ChildResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Child ID to update                    : ");
    final String firstName = console.readRequired("First name                           : ");
    final String secondName = console.readOptional("Second name (blank = none)           : ");
    final String firstSurname = console.readRequired("First surname                        : ");
    final String secondSurname = console.readOptional("Second surname (blank = none)        : ");
    final String birthDate = console.readRequired("Birth date (yyyy-MM-dd)              : ");
    final String admissionDate = console.readRequired("Admission date (yyyy-MM-dd)          : ");
    final String dischargeDate = console.readOptional("Discharge date (blank = none)        : ");
    final String address = console.readOptional("Address (blank = none)               : ");
    final String guardianId = console.readRequired("Guardian ID (CI)                     : ");
    final String relationship = console.readRequired("Relationship (FAMILIAR / CONOCIDO)  : ");

    try {
      final ChildResponse updated =
          childController.updateChild(
              new UpdateChildRequest(
                  id,
                  firstName,
                  secondName,
                  firstSurname,
                  secondSurname,
                  birthDate,
                  admissionDate,
                  dischargeDate,
                  address,
                  guardianId,
                  relationship));
      console.println("\n  Child updated successfully.");
      printer.print(updated);
    } catch (final ChildNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}

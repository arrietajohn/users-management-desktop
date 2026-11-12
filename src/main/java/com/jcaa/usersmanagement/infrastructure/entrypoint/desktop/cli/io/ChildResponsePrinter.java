package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ChildResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ChildResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-14s : %s%n";
  private static final String EMPTY_VALUE = "-";

  private final ConsoleIO console;

  public void print(final ChildResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID", response.id());
    console.printf(ROW_FORMAT, "Name", response.fullName());
    console.printf(ROW_FORMAT, "Birth date", response.birthDate());
    console.printf(ROW_FORMAT, "Admission date", response.admissionDate());
    console.printf(ROW_FORMAT, "Discharge date", valueOrDash(response.dischargeDate()));
    console.printf(ROW_FORMAT, "Address", valueOrDash(response.address()));
    console.printf(ROW_FORMAT, "Guardian ID", response.guardianId());
    console.printf(ROW_FORMAT, "Relationship", response.guardianRelationship());
    console.printf(ROW_FORMAT, "Status", response.status());
    console.println(SEPARATOR);
  }

  public void printList(final List<ChildResponse> children) {
    if (children.isEmpty()) {
      console.println("  No children found.");
      return;
    }
    console.printf("%n  Total: %d child(ren)%n", children.size());
    children.forEach(this::print);
  }

  private static String valueOrDash(final String value) {
    return Objects.isNull(value) ? EMPTY_VALUE : value;
  }
}

package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.PersonaResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class PersonaResponsePrinter {

  private static final String SEPARATOR = "-".repeat(60);
  private final ConsoleIO console;

  public void print(final PersonaResponse persona) {
    console.println(SEPARATOR);
    console.printf("  %-22s : %s%n", "ID", persona.idPersona());
    console.printf("  %-22s : %s%n", "Nombres", persona.nombres());
    console.printf("  %-22s : %s%n", "Apellidos", persona.apellidos());
    console.printf("  %-22s : %s%n", "Fecha nacimiento", persona.fechaNacimiento());
    console.printf("  %-22s : %s%n", "ID municipio", persona.idMunicipioNacimiento());
    console.printf("  %-22s : %s%n", "Edad", persona.edad());
    console.printf("  %-22s : %s%n", "Estatura", persona.estatura());
    console.printf("  %-22s : %s%n", "Sexo", persona.sexo());
    console.printf("  %-22s : %s%n", "Nivel estudios", persona.nivelEstudios());
    console.printf("  %-22s : %s%n", "Situacion militar", persona.situacionMilitar());
    console.printf("  %-22s : %s%n", "DNI", persona.dni());
    console.printf("  %-22s : %s%n", "Estado civil", persona.estadoCivil());
    console.printf("  %-22s : %s%n", "Ocupacion", persona.ocupacion());
    console.printf("  %-22s : %s%n", "Grupo etnico", persona.grupoEtnico());
    console.println(SEPARATOR);
  }

  public void printList(final List<PersonaResponse> personas) {
    if (personas.isEmpty()) {
      console.println("  No hay personas registradas.");
      return;
    }

    console.printf("%n  Total: %d persona(s)%n", personas.size());
    personas.forEach(this::print);
  }
}

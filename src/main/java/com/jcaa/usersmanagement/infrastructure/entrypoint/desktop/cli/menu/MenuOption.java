package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu;

import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuOption {

  LIST_USERS(1, "List all users"),
  FIND_USER(2, "Find user by ID"),
  CREATE_USER(3, "Create user"),
  UPDATE_USER(4, "Update user"),
  DELETE_USER(5, "Delete user"),
  LOGIN(6, "Login"),
  LIST_CANDIDATOS(7, "List all candidatos"),
  FIND_CANDIDATO(8, "Find candidato by ID"),
  CREATE_CANDIDATO(9, "Create candidato"),
  UPDATE_CANDIDATO(10, "Update candidato"),
  DELETE_CANDIDATO(11, "Delete candidato"),
  EXIT(0, "Exit");

  private final int number;
  private final String description;

  public static Optional<MenuOption> fromNumber(final int number) {
    for (final MenuOption option : values()) {
      if (option.number == number) {
        return Optional.of(option);
      }
    }
    return Optional.empty();
  }
}


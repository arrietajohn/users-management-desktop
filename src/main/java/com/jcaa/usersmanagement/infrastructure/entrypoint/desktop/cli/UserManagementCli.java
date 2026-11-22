package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.DeleteUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindUserByIdHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListUsersHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.LoginHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu.MenuOption;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.CandidatoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateCandidatoHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.DeleteCandidatoHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindCandidatoByIdHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListCandidatosHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateCandidatoHandler;

@RequiredArgsConstructor
public final class UserManagementCli {

  private static final String BANNER =
      """
      ==========================================
           Users Management System
      ==========================================""";

  private static final String MENU_BORDER = "  ==========================================";

  private final UserController userController;
  private final CandidatoController candidatoController;
  private final ConsoleIO console;

  public void start() {
    console.println(BANNER);
    final UserResponsePrinter printer = new UserResponsePrinter(console);
    runLoop(buildHandlers(printer));
  }

  private void runLoop(final Map<MenuOption, OperationHandler> handlers) {
    boolean running = true;
    while (running) {
      printMenu();
      final int choice = console.readInt("\n  Option: ");
      final Optional<MenuOption> option = MenuOption.fromNumber(choice);

      if (option.isEmpty()) {
        console.println("  Invalid option. Please try again.");
      } else if (option.get() == MenuOption.EXIT) {
        console.println("\n  Goodbye!\n");
        running = false;
      } else {
        executeHandler(handlers, option.get());
      }
    }
  }

  private void executeHandler(
      final Map<MenuOption, OperationHandler> handlers, final MenuOption option) {
    try {
      handlers.get(option).handle();
    } catch (final ConstraintViolationException exception) {
      console.println("  Validation errors:");
      exception.getConstraintViolations()
          .forEach(violation -> console.println("    - " + violation.getMessage()));
    } catch (final RuntimeException exception) {
      console.println("  Unexpected error: " + exception.getMessage());
    }
  }

  private Map<MenuOption, OperationHandler> buildHandlers(final UserResponsePrinter printer) {
    return Map.ofEntries(
            Map.entry(MenuOption.LIST_USERS,        new ListUsersHandler(userController, printer)),
            Map.entry(MenuOption.FIND_USER,         new FindUserByIdHandler(userController, console, printer)),
            Map.entry(MenuOption.CREATE_USER,       new CreateUserHandler(userController, console, printer)),
            Map.entry(MenuOption.UPDATE_USER,       new UpdateUserHandler(userController, console, printer)),
            Map.entry(MenuOption.DELETE_USER,       new DeleteUserHandler(userController, console)),
            Map.entry(MenuOption.LOGIN,             new LoginHandler(userController, console, printer)),
            Map.entry(MenuOption.LIST_CANDIDATOS,   new ListCandidatosHandler(candidatoController, console)),
            Map.entry(MenuOption.FIND_CANDIDATO,    new FindCandidatoByIdHandler(candidatoController, console)),
            Map.entry(MenuOption.CREATE_CANDIDATO,  new CreateCandidatoHandler(candidatoController, console)),
            Map.entry(MenuOption.UPDATE_CANDIDATO,  new UpdateCandidatoHandler(candidatoController, console)),
            Map.entry(MenuOption.DELETE_CANDIDATO,  new DeleteCandidatoHandler(candidatoController, console)));
  }

  private void printMenu() {
    console.println();
    console.println(MENU_BORDER);
    console.println("    Main Menu");
    console.println(MENU_BORDER);
    for (final MenuOption option : MenuOption.values()) {
      console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
    }
    console.println(MENU_BORDER);
  }
}
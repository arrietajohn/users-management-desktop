/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmpleadoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmpleadoResponsePrinter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteEmpleadoHandler implements OperationHandler {

    private final EmpleadoController empleadoController;
    private final ConsoleIO console;
    private final EmpleadoResponsePrinter printer;

    @Override
    public void handle() {
        console.println("\n--- ELIMINAR EMPLEADO ---");

        String id = console.readRequired("ID del empleado: ");

        empleadoController.delete(id);

        printer.printMessage("Empleado eliminado correctamente.");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmpleadoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmpleadoResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.EmpleadoDesktopMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindEmpleadoByIdHandler implements OperationHandler {

    private final EmpleadoController empleadoController;
    private final ConsoleIO console;
    private final EmpleadoResponsePrinter printer;

    @Override
    public void handle() {

        console.println("\n--- BUSCAR EMPLEADO ---");

        String id = console.readRequired("Ingrese el ID del empleado: ");

        EmpleadoModel empleado = empleadoController.getById(id);

        printer.printEmpleado(
                EmpleadoDesktopMapper.fromModelToResponse(empleado)
        );
    }
}

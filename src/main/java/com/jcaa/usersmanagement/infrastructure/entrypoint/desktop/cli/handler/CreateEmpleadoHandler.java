/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmpleadoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateEmpleadoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmpleadoResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.EmpleadoDesktopMapper;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEmpleadoHandler implements OperationHandler {

    private final EmpleadoController empleadoController;
    private final ConsoleIO console;
    private final EmpleadoResponsePrinter printer;

    @Override
    public void handle() {

        console.println("\n--- REGISTRAR EMPLEADO ---");

        String id = console.readRequired("ID: ");
        String nombre = console.readRequired("Nombre: ");
        String documento = console.readRequired("Documento: ");
        String cargo = console.readRequired("Cargo: ");
        BigDecimal salario = new BigDecimal(
                console.readRequired("Salario: ")
        );

        CreateEmpleadoRequest request = new CreateEmpleadoRequest(
                id, nombre, documento, cargo, salario
        );

        EmpleadoModel empleado =
                EmpleadoDesktopMapper.fromCreateRequestToModel(request);

        EmpleadoModel creado = empleadoController.create(empleado);

        printer.printEmpleado(
                EmpleadoDesktopMapper.fromModelToResponse(creado)
        );

        printer.printMessage("Empleado registrado correctamente.");
    }
}
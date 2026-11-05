/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmpleadoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateEmpleadoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmpleadoResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.EmpleadoDesktopMapper;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmpleadoResponsePrinter;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateEmpleadoHandler implements OperationHandler {

    private final EmpleadoController empleadoController;
    private final ConsoleIO console;
    private final EmpleadoResponsePrinter printer;

    @Override
    public void handle() {
        console.println("\n--- ACTUALIZAR EMPLEADO ---");

        String id = console.readRequired("ID del empleado: ");
        String nombre = console.readRequired("Nuevo nombre: ");
        String documento = console.readRequired("Nuevo documento: ");
        String cargo = console.readRequired("Nuevo cargo: ");
        BigDecimal salario = new BigDecimal(
                console.readRequired("Nuevo salario: "));

        UpdateEmpleadoRequest request = new UpdateEmpleadoRequest(
                id, nombre, documento, cargo, salario);

        EmpleadoModel empleado = EmpleadoDesktopMapper
                .fromUpdateRequestToModel(request);

        EmpleadoModel actualizado = empleadoController.update(empleado);

        EmpleadoResponse response = EmpleadoDesktopMapper
                .fromModelToResponse(actualizado);

        printer.printEmpleado(response);
        printer.printMessage("Empleado actualizado correctamente.");
    }
}

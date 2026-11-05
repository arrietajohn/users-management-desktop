/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmpleadoResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmpleadoController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.EmpleadoDesktopMapper;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmpleadoResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListEmpleadosHandler implements OperationHandler {

    private final EmpleadoController empleadoController;
    private final EmpleadoResponsePrinter printer;

    @Override
    public void handle() {
        List<EmpleadoModel> empleados = empleadoController.getAll();

        List<EmpleadoResponse> respuestas = empleados.stream()
                .map(EmpleadoDesktopMapper::fromModelToResponse)
                .collect(Collectors.toList());

        printer.printEmpleados(respuestas);
    }
}

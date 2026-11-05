/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;
import com.jcaa.usersmanagement.application.port.in.CreateEmpleadoUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEmpleadoByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllEmpleadosUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEmpleadoUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEmpleadoUseCase;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmpleadoController {

    private final CreateEmpleadoUseCase createEmpleadoUseCase;
    private final GetEmpleadoByIdUseCase getEmpleadoByIdUseCase;
    private final GetAllEmpleadosUseCase getAllEmpleadosUseCase;
    private final UpdateEmpleadoUseCase updateEmpleadoUseCase;
    private final DeleteEmpleadoUseCase deleteEmpleadoUseCase;

    public EmpleadoModel create(EmpleadoModel empleado) {
        return createEmpleadoUseCase.create(empleado);
    }

    public EmpleadoModel getById(String id) {
        return getEmpleadoByIdUseCase.getById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Empleado no encontrado"));
    }

    public List<EmpleadoModel> getAll() {
        return getAllEmpleadosUseCase.getAll();
    }

    public EmpleadoModel update(EmpleadoModel empleado) {
        return updateEmpleadoUseCase.update(empleado);
    }

    public void delete(String id) {
        deleteEmpleadoUseCase.delete(id);
    }
}

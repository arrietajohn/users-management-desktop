package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateEmpleadoUseCase;
import com.jcaa.usersmanagement.application.port.out.UpdateEmpleadoPort;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateEmpleadoService implements UpdateEmpleadoUseCase {

    private final UpdateEmpleadoPort updateEmpleadoPort;

    @Override
    public EmpleadoModel update(EmpleadoModel empleado) {
        return updateEmpleadoPort.update(empleado);
    }
}
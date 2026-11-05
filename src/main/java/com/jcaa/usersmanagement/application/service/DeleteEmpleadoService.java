package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteEmpleadoUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteEmpleadoPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteEmpleadoService implements DeleteEmpleadoUseCase {

    private final DeleteEmpleadoPort deleteEmpleadoPort;

    @Override
    public void delete(String id) {
        deleteEmpleadoPort.delete(id);
    }
}

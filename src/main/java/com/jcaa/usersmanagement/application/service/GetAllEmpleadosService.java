package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllEmpleadosUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllEmpleadosPort;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllEmpleadosService implements GetAllEmpleadosUseCase {

    private final GetAllEmpleadosPort getAllEmpleadosPort;

    @Override
    public List<EmpleadoModel> getAll() {
        return getAllEmpleadosPort.getAll();
    }
}
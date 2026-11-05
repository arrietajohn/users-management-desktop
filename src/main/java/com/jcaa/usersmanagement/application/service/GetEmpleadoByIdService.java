package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetEmpleadoByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEmpleadoByIdPort;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetEmpleadoByIdService implements GetEmpleadoByIdUseCase {

    private final GetEmpleadoByIdPort getEmpleadoByIdPort;

    @Override
    public Optional<EmpleadoModel> getById(String id) {
        return getEmpleadoByIdPort.getById(id);
    }
}
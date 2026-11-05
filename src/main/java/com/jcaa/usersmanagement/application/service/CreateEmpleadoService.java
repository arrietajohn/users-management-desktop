/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateEmpleadoUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveEmpleadoPort;
import com.jcaa.usersmanagement.application.port.out.GetEmpleadoByDocumentoPort;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEmpleadoService implements CreateEmpleadoUseCase {

    private final SaveEmpleadoPort saveEmpleadoPort;
    private final GetEmpleadoByDocumentoPort getEmpleadoByDocumentoPort;

    @Override
    public EmpleadoModel create(EmpleadoModel empleado) {

        if (getEmpleadoByDocumentoPort
                .getByDocumento(empleado.getDocumento()).isPresent()) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }

        return saveEmpleadoPort.save(empleado);
    }
}
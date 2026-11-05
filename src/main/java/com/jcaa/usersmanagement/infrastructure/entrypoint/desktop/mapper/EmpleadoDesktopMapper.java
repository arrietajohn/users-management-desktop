/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateEmpleadoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateEmpleadoRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmpleadoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmpleadoDesktopMapper {

    public EmpleadoModel fromCreateRequestToModel(CreateEmpleadoRequest request) {
        return new EmpleadoModel(
                request.getId(),
                request.getNombre(),
                request.getDocumento(),
                request.getCargo(),
                request.getSalario()
        );
    }

    public EmpleadoModel fromUpdateRequestToModel(UpdateEmpleadoRequest request) {
        return new EmpleadoModel(
                request.getId(),
                request.getNombre(),
                request.getDocumento(),
                request.getCargo(),
                request.getSalario()
        );
    }

    public EmpleadoResponse fromModelToResponse(EmpleadoModel empleado) {
        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getDocumento(),
                empleado.getCargo(),
                empleado.getSalario()
        );
    }
}

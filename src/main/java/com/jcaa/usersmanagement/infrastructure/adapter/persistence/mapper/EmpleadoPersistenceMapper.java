/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EmpleadoPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EmpleadoEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmpleadoPersistenceMapper {

    public EmpleadoPersistenceDto fromModelToDto(EmpleadoModel empleado) {
        return new EmpleadoPersistenceDto(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getDocumento(),
                empleado.getCargo(),
                empleado.getSalario()
        );
    }

    public EmpleadoModel fromEntityToModel(EmpleadoEntity entity) {
        return new EmpleadoModel(
                entity.id(),
                entity.nombre(),
                entity.documento(),
                entity.cargo(),
                entity.salario()
        );
    }
}
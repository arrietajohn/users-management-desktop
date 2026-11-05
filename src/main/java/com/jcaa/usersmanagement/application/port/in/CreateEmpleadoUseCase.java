/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jcaa.usersmanagement.application.port.in;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;

public interface CreateEmpleadoUseCase {

    EmpleadoModel create(EmpleadoModel empleado);
}
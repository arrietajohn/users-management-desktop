/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jcaa.usersmanagement.application.port.out;
import com.jcaa.usersmanagement.domain.model.EmpleadoModel;
import java.util.List;

public interface GetAllEmpleadosPort {

    List<EmpleadoModel> getAll();
}

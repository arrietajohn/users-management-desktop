/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmpleadoResponse;
import java.util.List;

public class EmpleadoResponsePrinter {

    public void printEmpleado(EmpleadoResponse empleado) {
        System.out.println("\n--- DATOS DEL EMPLEADO ---");
        System.out.println("ID: " + empleado.getId());
        System.out.println("Nombre: " + empleado.getNombre());
        System.out.println("Documento: " + empleado.getDocumento());
        System.out.println("Cargo: " + empleado.getCargo());
        System.out.println("Salario: " + empleado.getSalario());
    }

    public void printEmpleados(List<EmpleadoResponse> empleados) {
        System.out.println("\n--- LISTA DE EMPLEADOS ---");

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        empleados.forEach(this::printEmpleado);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}

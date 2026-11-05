/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.domain.model;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *
 * @author USER
 */
class EmpleadoModelTest {

    @Test
    void debeCrearEmpleadoCorrectamente() {
        EmpleadoModel empleado = new EmpleadoModel(
                "EMP-001",
                "Aldair Anaya",
                "1234567890",
                "Auxiliar",
                new BigDecimal("1500000")
        );

        assertEquals("EMP-001", empleado.getId());
        assertEquals("Aldair Anaya", empleado.getNombre());
        assertEquals("1234567890", empleado.getDocumento());
        assertEquals("Auxiliar", empleado.getCargo());
        assertEquals(new BigDecimal("1500000"), empleado.getSalario());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;
import java.math.BigDecimal;

public record EmpleadoEntity(
        String id,
        String nombre,
        String documento,
        String cargo,
        BigDecimal salario) {
}
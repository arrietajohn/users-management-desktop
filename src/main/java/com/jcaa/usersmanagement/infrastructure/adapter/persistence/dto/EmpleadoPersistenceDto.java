/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;
import java.math.BigDecimal;

public record EmpleadoPersistenceDto(
        String id,
        String nombre,
        String documento,
        String cargo,
        BigDecimal salario) {
}
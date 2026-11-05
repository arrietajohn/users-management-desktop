/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

/**
 *
 * @author USER
 */
public final class EmpleadoPersistenceException extends RuntimeException {

    private EmpleadoPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EmpleadoPersistenceException becauseSaveFailed(String id, Throwable cause) {
        return new EmpleadoPersistenceException(
                "Error al guardar el empleado con ID: " + id, cause);
    }

    public static EmpleadoPersistenceException becauseUpdateFailed(String id, Throwable cause) {
        return new EmpleadoPersistenceException(
                "Error al actualizar el empleado con ID: " + id, cause);
    }

    public static EmpleadoPersistenceException becauseFindFailed(String id, Throwable cause) {
        return new EmpleadoPersistenceException(
                "Error al buscar el empleado con ID: " + id, cause);
    }

    public static EmpleadoPersistenceException becauseFindAllFailed(Throwable cause) {
        return new EmpleadoPersistenceException(
                "Error al consultar los empleados.", cause);
    }

    public static EmpleadoPersistenceException becauseDeleteFailed(String id, Throwable cause) {
        return new EmpleadoPersistenceException(
                "Error al eliminar el empleado con ID: " + id, cause);
    }
}

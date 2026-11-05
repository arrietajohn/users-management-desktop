-- =============================================
-- Script de creación de la base de datos
-- Gestión de Usuarios y Empleados
-- Arquitectura Hexagonal
-- =============================================

CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

-- =============================================
-- Tabla de usuarios
-- =============================================

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Tabla de empleados
-- =============================================

CREATE TABLE IF NOT EXISTS empleados (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    documento   VARCHAR(30)  NOT NULL UNIQUE,
    cargo       VARCHAR(100) NOT NULL,
    salario     DECIMAL(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Usuario administrador inicial
-- Password: Admin1234!
-- =============================================

INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$placeholderHashReplaceWithRealBCryptHash',
    'ADMIN',
    'ACTIVE'
);
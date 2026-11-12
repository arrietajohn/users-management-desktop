-- =============================================================
-- guarderia_app: base de datos reducida para conectarla
-- a la app de DSW (Arquitectura Hexagonal + DDD)
-- Motor: MySQL 8.0.16+ (usa CHECK)
-- Contiene: users (del repo base) + persona, responsable_pago, nino
-- =============================================================

CREATE DATABASE IF NOT EXISTS guarderia_app
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE guarderia_app;

-- -------------------------------------------------------------
-- 1. Tabla de usuarios del repo base (casos de uso de User)
-- -------------------------------------------------------------
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

-- Usuario administrador inicial (correo: admin@example.com, password: Admin1234!)
INSERT IGNORE INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$PLjvljaEPHegWgtfGU0/K.clnpg52Xd2cMPecWgNBYiUSllqoFNOi',
    'ADMIN',
    'ACTIVE'
);

-- -------------------------------------------------------------
-- 2. Tablas del CEA Guardería necesarias para la entidad Nino
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS persona (
    ci               VARCHAR(20)  NOT NULL,
    primer_nombre    VARCHAR(50)  NOT NULL,
    segundo_nombre   VARCHAR(50)  DEFAULT NULL,
    primer_apellido  VARCHAR(50)  NOT NULL,
    segundo_apellido VARCHAR(50)  DEFAULT NULL,
    direccion        VARCHAR(150) NOT NULL,
    PRIMARY KEY (ci)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS responsable_pago (
    ci                      VARCHAR(20)    NOT NULL,
    cuenta_bancaria_cifrada VARBINARY(255) NOT NULL,
    PRIMARY KEY (ci),
    -- Un responsable de pago es siempre una persona; no se borra una persona
    -- con rol de responsable (RESTRICT) y un cambio de CI se propaga (CASCADE).
    CONSTRAINT fk_responsable_pago_persona FOREIGN KEY (ci)
        REFERENCES persona (ci) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS nino (
    matricula            VARCHAR(20) NOT NULL,
    primer_nombre        VARCHAR(50) NOT NULL,
    segundo_nombre       VARCHAR(50) DEFAULT NULL,
    primer_apellido      VARCHAR(50) NOT NULL,
    segundo_apellido     VARCHAR(50) DEFAULT NULL,
    fecha_nacimiento     DATE        NOT NULL,
    fecha_ingreso        DATE        NOT NULL,
    fecha_baja           DATE        DEFAULT NULL,
    direccion            VARCHAR(150) DEFAULT NULL,
    ci_responsable       VARCHAR(20) NOT NULL,
    relacion_responsable VARCHAR(10) NOT NULL,
    PRIMARY KEY (matricula),
    KEY idx_nino_ci_responsable (ci_responsable),
    -- No se elimina un responsable que tenga niños a cargo (RESTRICT).
    CONSTRAINT fk_nino_responsable FOREIGN KEY (ci_responsable)
        REFERENCES responsable_pago (ci) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT nino_chk_1 CHECK (relacion_responsable IN ('FAMILIAR', 'CONOCIDO')),
    CONSTRAINT nino_chk_2 CHECK (fecha_nacimiento < fecha_ingreso),
    CONSTRAINT nino_chk_3 CHECK (fecha_baja IS NULL OR fecha_baja >= fecha_ingreso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------------------------------------------------
-- 3. Datos semilla: dos responsables de pago para poder matricular niños
-- -------------------------------------------------------------
INSERT IGNORE INTO persona (ci, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, direccion)
VALUES
    ('1001', 'Maria', 'Fernanda', 'Gomez',  'Perez',   'Calle 10 # 5-20'),
    ('1002', 'Carlos', NULL,      'Ramirez', 'Lopez',   'Carrera 7 # 12-45');

INSERT IGNORE INTO responsable_pago (ci, cuenta_bancaria_cifrada)
VALUES
    ('1001', CAST('CUENTA-DEMO-CIFRADA-1' AS BINARY)),
    ('1002', CAST('CUENTA-DEMO-CIFRADA-2' AS BINARY));

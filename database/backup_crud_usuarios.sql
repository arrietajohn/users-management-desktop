CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED')
                NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS persona (
    id_persona              INT NOT NULL PRIMARY KEY,
    nombres                 VARCHAR(255) NOT NULL,
    apellidos               VARCHAR(255) NOT NULL,
    fecha_nacimiento        DATE NOT NULL,
    id_municipio_nacimiento INT NOT NULL,
    edad                    INT NOT NULL,
    estatura                DECIMAL(8,2) NULL,
    sexo                    VARCHAR(100) NULL,
    nivel_estudios          VARCHAR(255) NULL,
    situacion_militar       VARCHAR(255) NULL,
    dni                     VARCHAR(100) NULL,
    estado_civil            VARCHAR(100) NULL,
    ocupacion               VARCHAR(255) NULL,
    grupo_etnico            VARCHAR(255) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
# users-menagement-desktop-hezagonal-ddd
Ejemplo de una aplicación Desktop que hace CRUDL y control de acceso de Usuarios, escrita en Java,  arquitectura Hexagonal y enfoque DDD, usando buenas practicas y Clean code
Este es un ejemplo de Proyecto Java con hexagonal para los alumnos de UdeC

---

## Extensión: gestión de niños (CEA Guardería)

Este fork agrega la entidad **`Child`** (tabla `nino` del CEA Guardería) con las operaciones CRUDL
(crear, buscar, listar, actualizar y eliminar), siguiendo la misma arquitectura hexagonal y las
mismas convenciones que la entidad `User`. Los casos de uso de `User` no se modificaron.

La persistencia usa **MySQL con JDBC puro** (sin ORM).

### Requisitos

- JDK 17 o superior (probado con JDK 21)
- Maven 3.9 o superior
- MySQL 8.0.16 o superior (las restricciones `CHECK` se aplican desde esa versión)

### 1. Preparar la base de datos

El script oficial es `database/guarderia_app.sql`. Crea la base `guarderia_app` con las tablas que usa la
aplicación (`users`, `persona`, `responsable_pago` y `nino`) y datos semilla.

```
# Windows (símbolo del sistema o PowerShell)
cmd /c "mysql -u root -p < database\guarderia_app.sql"

# Linux / macOS
mysql -u root -p < database/guarderia_app.sql
```

También se incluye `database/backup_guarderia_app.sql`, una copia de seguridad (`mysqldump`) de la
misma base, que se restaura con el mismo comando.

Después crea un usuario de MySQL para la aplicación:

```sql
CREATE USER 'guarderia_app'@'localhost' IDENTIFIED BY 'tu_contraseña';
GRANT SELECT, INSERT, UPDATE, DELETE ON guarderia_app.* TO 'guarderia_app'@'localhost';
```

Datos semilla: usuario `admin@example.com` (contraseña `Admin1234!`) y dos responsables de pago
con cédula `1001` y `1002`.

> `src/main/resources/schema.sql` es el script original del repositorio base (crea `crud_usuarios`)
> y se conserva sin cambios. Para ejecutar este proyecto se usa `database/guarderia_app.sql`.

### 2. Configurar la aplicación

Edita `src/main/resources/application.properties` **solo en tu equipo** con los datos de tu MySQL:

```
db.host=localhost
db.port=3306
db.name=guarderia_app
db.username=guarderia_app
db.password=tu_contraseña
```

No subas tus credenciales al repositorio. La sección `smtp.*` solo la usan los casos de uso de `User`
(correos al crear o actualizar un usuario). Si no configuras un SMTP válido, esas operaciones se
guardan en la base, pero muestran un mensaje de error de envío de correo. No afecta a `Child`.

### 3. Ejecutar

Ejecuta la clase `com.jcaa.usersmanagement.Main` desde el IDE. El menú ofrece:

| Opción | Operación |
|---|---|
| 1 a 6 | Usuarios: listar, buscar, crear, actualizar, eliminar y login |
| 7 | Listar niños |
| 8 | Buscar niño por matrícula |
| 9 | Crear niño |
| 10 | Actualizar niño |
| 11 | Eliminar niño |
| 0 | Salir |

### 4. Pruebas

```
mvn test      # ejecuta las pruebas unitarias
mvn verify    # además genera el reporte de cobertura en target/site/jacoco/index.html
```

Las pruebas usan JUnit 5 y Mockito, y no requieren base de datos.

---

## Arquitectura de `Child`

```
domain/
  model/          ChildModel                      (agregado; valida sus invariantes)
  valueobject/    ChildId, ChildFullName, ChildAddress, GuardianId
  enums/          GuardianRelationship            (FAMILIAR, CONOCIDO)
  event/          ChildCreated / ChildUpdated / ChildDeleted DomainEvent
  exception/      Invalid*Exception, ChildNotFoundException, ChildAlreadyExistsException
application/
  port/in/        CreateChildUseCase, GetChildByIdUseCase, GetAllChildrenUseCase,
                  UpdateChildUseCase, DeleteChildUseCase                 (puertos de entrada)
  port/out/       SaveChildPort, UpdateChildPort, GetChildByIdPort,
                  GetAllChildrenPort, DeleteChildPort                   (puertos de salida)
  service/        *ChildService (implementan los casos de uso), dto/command, dto/query, mapper
infrastructure/
  adapter/persistence/   ChildRepositoryMySQL (adaptador JDBC), dto, entity, mapper
  entrypoint/desktop/    ChildController, handlers de la CLI, DTO y mappers
  config/                DependencyContainer (ensamblaje manual de dependencias)
```

Flujo de una operación (por ejemplo, crear un niño):

```
CLI (CreateChildHandler)
  → ChildController
    → CreateChildUseCase          (puerto de entrada)
      → CreateChildService        (aplicación)
        → SaveChildPort           (puerto de salida)
          → ChildRepositoryMySQL  (adaptador JDBC)
            → MySQL
```

Las dependencias apuntan siempre hacia adentro: `infrastructure` depende de `application`, y
`application` depende de `domain`. El dominio no depende de ningún framework ni de JDBC.

### Reglas de negocio

- La matrícula (`ChildId`) identifica al niño y no puede repetirse.
- La fecha de nacimiento debe ser estrictamente anterior a la fecha de ingreso.
- La fecha de baja es opcional y, si existe, debe ser igual o posterior a la fecha de ingreso.
- La relación con el responsable solo puede ser `FAMILIAR` o `CONOCIDO`.
- El responsable debe existir previamente en `responsable_pago`.
- Segundo nombre, segundo apellido y dirección son opcionales (se guardan como `NULL`).

### Decisiones de diseño

- `ChildModel` valida sus invariantes en un constructor explícito, de modo que ninguna ruta
  (crear, actualizar o reconstruir desde la base) pueda producir un niño inválido.
- Las fechas viajan como texto `yyyy-MM-dd` en los DTO y se convierten a `LocalDate` en los mappers.
- Los eventos de dominio de `Child` están definidos y probados, pero ningún servicio los publica
  (igual que ocurre con los de `User`).

### Flujo de trabajo en Git

Ramas cortas por caso de uso o capa (`feat/create-child-usecase`, `feat/child-persistence-adapter`...),
Pull Requests hacia `main` de este fork con un máximo de 10 archivos, y mensajes de commit con
Conventional Commits (`feat:`, `test:`, `docs:`, `chore:`).

### Limitaciones conocidas

- Crear un niño con un responsable inexistente falla por la llave foránea (integridad referencial).
- No hay pruebas del repositorio contra una base de datos real.

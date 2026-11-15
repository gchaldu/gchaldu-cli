Estado: aprobado

# Plan 001: esqueleto

## Notas generales

- Cada tarea sigue el orden **test primero**: se escribe el test que define el comportamiento esperado y luego la implementación mínima que lo hace pasar.
- Los tests de aceptación no hardcodean `0.1.0-SNAPSHOT`; leen la versión desde la propiedad de sistema `project.version` que inyecta Surefire mediante `<systemPropertyVariables>`.
- La versión de `actions/setup-java` es la **v6** (última mayor confirmada en GitHub Releases: `v6.3.0`).

## Tareas

- [x] **T1: Crear `pom.xml` base y Maven Wrapper**
  - Archivos: `pom.xml`, `mvnw`, `mvnw.cmd`, `.mvn/wrapper/maven-wrapper.properties` (y `MavenWrapperDownloader.java` si aplica).
  - Qué hace: define `groupId`, `artifactId`, `version` (`0.1.0-SNAPSHOT`), Java 25 (`<release>25</release>`), `maven-compiler-plugin` 3.16.0, `maven-surefire-plugin` 3.6.1 con `<systemPropertyVariables><project.version>${project.version}</project.version></systemPropertyVariables>`, y las propiedades de versiones de Picocli 4.7.7, JUnit 6.1.3 y AssertJ 3.27.7. Genera el wrapper con Maven 3.9.16.
  - Test: `./mvnw verify` pasa.
  - Criterios: **C1** (parcial), **C5**.

- [x] **T2: Implementar `--version` con recurso filtrado e `IVersionProvider`**
  - Archivos:
    - Test: `src/test/java/io/github/gchaldu/cli/GchalduCommandVersionTest.java`.
    - Implementación: `src/main/resources/app.properties`, `pom.xml` (resource filtering), `src/main/java/io/github/gchaldu/cli/GchalduCommand.java`.
  - Qué hace: el test ejecuta `CommandLine.execute("--version")` y verifica que la salida contenga `gchaldu`, la versión leída de `System.getProperty("project.version")`, y que el código de salida sea `0`. Si la propiedad `project.version` no está definida (por ejemplo, al correr el test desde IntelliJ sin Maven), el test falla con un mensaje claro como "La propiedad 'project.version' no está definida; ejecutá el test con Maven.". Luego se agrega `app.properties` con `version=${project.version}`, se habilita el filtrado de recursos en `pom.xml` y se configura `GchalduCommand` con `@Command(versionProvider = GchalduVersionProvider.class)` para mostrar `gchaldu <version>`.
  - Test: `./mvnw test -Dtest=GchalduCommandVersionTest`.
  - Criterios: **C2**, **C5**.

- [x] **T3: Implementar `--help` en castellano**
  - Archivos:
    - Test: `src/test/java/io/github/gchaldu/cli/GchalduCommandHelpTest.java`.
    - Implementación: `src/main/java/io/github/gchaldu/cli/GchalduCommand.java`.
  - Qué hace: el test ejecuta `CommandLine.execute("--help")` y verifica que la salida contenga `Uso:`, `--version`, `--help` y que el código de salida sea `0`. Luego se configuran en `GchalduCommand` los encabezados y descripciones en castellano (`synopsisHeading = "Uso: "`, `optionListHeading = "Opciones:"`, descripciones de `--help` y `--version`).
  - Test: `./mvnw test -Dtest=GchalduCommandHelpTest`.
  - Criterios: **C3**.

- [x] **T4: Implementar opción desconocida en castellano**
  - Archivos:
    - Test: `src/test/java/io/github/gchaldu/cli/GchalduCommandUnknownOptionTest.java`.
    - Implementación: `src/main/java/io/github/gchaldu/cli/GchalduCommand.java` (o una clase `GchalduParameterExceptionHandler.java`).
  - Qué hace: el test ejecuta `CommandLine.execute("--opcion-desconocida")` y verifica que la salida contenga `Opción desconocida '--opcion-desconocida'` y que el código de salida sea `2`. Luego se registra un `IParameterExceptionHandler` que reemplaza el mensaje en inglés de Picocli por el mensaje en castellano.
  - Test: `./mvnw test -Dtest=GchalduCommandUnknownOptionTest`.
  - Criterios: **C4**.

- [x] **T5: Crear `Main` y configurar el jar ejecutable con Shade**
  - Archivos:
    - Implementación: `src/main/java/io/github/gchaldu/Main.java`, `pom.xml`.
  - Qué hace: `Main` crea `CommandLine` con `GchalduCommand` y llama a `execute(args)`. En `pom.xml` se agrega `maven-shade-plugin` 3.6.2 con `Main-Class: io.github.gchaldu.Main`, `finalName: gchaldu` y `<createDependencyReducedPom>false</createDependencyReducedPom>`.
  - Test: `./mvnw verify` genera `target/gchaldu.jar`; luego se ejecutan `java -jar target/gchaldu.jar --version`, `java -jar target/gchaldu.jar --help` y `java -jar target/gchaldu.jar --opcion-desconocida`, verificando salidas y códigos de salida.
  - Criterios: **C2**, **C3**, **C4**, **C5**.

- [x] **T6: Crear el workflow de CI**
  - Archivos: `.github/workflows/ci.yml`.
  - Qué hace: corre `./mvnw verify` con `actions/setup-java@v6`, distribución `temurin`, Java 25 y `check-latest: true`, en `ubuntu-latest`, `macos-latest` y `windows-latest`. Cada job usa `shell: bash`. Se ejecuta en cada `push` y `pull_request`.
  - Test: se verifica al hacer push/PR; localmente `./mvnw verify` sigue en verde.
  - Criterios: **C1**, **C6**.

## Cobertura de criterios

| Criterio | Tareas |
| --- | --- |
| C1 | T1, T6 |
| C2 | T2, T5 |
| C3 | T3, T5 |
| C4 | T4, T5 |
| C5 | T1, T2, T5 |
| C6 | T6 |

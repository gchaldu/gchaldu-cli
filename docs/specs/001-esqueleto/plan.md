Estado: borrador

# Plan 001: esqueleto

## Tareas

- [ ] **T1: Crear el `pom.xml` base**
  - Archivos: `pom.xml`.
  - Qué hace: define `groupId`, `artifactId`, `version` (`0.1.0-SNAPSHOT`), Java 25 (`<release>25</release>`), `maven-compiler-plugin` 3.16.0, `maven-surefire-plugin` 3.6.1, y las propiedades de versiones de Picocli 4.7.7, JUnit 6.1.3 y AssertJ 3.27.7.
  - Test: `./mvnw verify` pasa (sin fuentes aún).
  - Criterios: **C1** (parcial), **C5**.

- [ ] **T2: Generar el Maven Wrapper**
  - Archivos: `mvnw`, `mvnw.cmd`, `.mvn/wrapper/maven-wrapper.properties`, `.mvn/wrapper/MavenWrapperDownloader.java` (si aplica).
  - Qué hace: fija Maven 3.9.16 como versión del wrapper.
  - Test: `./mvnw -version` muestra `Apache Maven 3.9.16`.
  - Criterios: **C1** (parcial).

- [ ] **T3: Agregar recurso filtrado `app.properties`**
  - Archivos: `src/main/resources/app.properties`, `pom.xml` (resource filtering).
  - Qué hace: contiene `version=${project.version}`; Maven reemplaza el placeholder durante el build.
  - Test: `./mvnw process-resources` y lectura de `target/classes/app.properties` con AssertJ para verificar que contiene `0.1.0-SNAPSHOT`.
  - Criterios: **C5**.

- [ ] **T4: Crear el comando raíz `GchalduCommand` con localización al castellano**
  - Archivos: `src/main/java/io/github/gchaldu/cli/GchalduCommand.java`.
  - Qué hace: anota el comando con `name = "gchaldu"`, `@Command(versionProvider = ...)`, encabezados `Uso:`, descripciones de `--help` y `--version` en castellano, y un `IParameterExceptionHandler` que traduce el error de opción desconocida.
  - Test: test manual con `CommandLine.execute` (se cubre formalmente en T6, T7 y T8).
  - Criterios: **C2**, **C3**, **C4** (parcial).

- [ ] **T5: Crear la clase de entrada `Main`**
  - Archivos: `src/main/java/io/github/gchaldu/Main.java`.
  - Qué hace: crea `CommandLine` con `GchalduCommand` y llama a `execute(args)`; asigna `System.exit` según el código retornado.
  - Test: `./mvnw compile` pasa.
  - Criterios: **C2**, **C3**, **C4** (parcial).

- [ ] **T6: Test de aceptación `--version`**
  - Archivos: `src/test/java/io/github/gchaldu/cli/GchalduCommandVersionTest.java`.
  - Qué hace: ejecuta `CommandLine.execute("--version")`, captura salida y código de salida, y verifica que contiene `gchaldu` y `0.1.0-SNAPSHOT` y que el código es `0`.
  - Test: `./mvnw test -Dtest=GchalduCommandVersionTest`.
  - Criterios: **C2**.

- [ ] **T7: Test de aceptación `--help`**
  - Archivos: `src/test/java/io/github/gchaldu/cli/GchalduCommandHelpTest.java`.
  - Qué hace: ejecuta `CommandLine.execute("--help")`, captura salida y código de salida, y verifica que contiene `Uso:`, `--version` y `--help`, y que el código es `0`.
  - Test: `./mvnw test -Dtest=GchalduCommandHelpTest`.
  - Criterios: **C3**.

- [ ] **T8: Test de aceptación opción desconocida**
  - Archivos: `src/test/java/io/github/gchaldu/cli/GchalduCommandUnknownOptionTest.java`.
  - Qué hace: ejecuta `CommandLine.execute("--opcion-desconocida")`, captura salida y código de salida, y verifica que contiene `Opción desconocida '--opcion-desconocida'` y que el código es `2`.
  - Test: `./mvnw test -Dtest=GchalduCommandUnknownOptionTest`.
  - Criterios: **C4**.

- [ ] **T9: Configurar Maven Shade Plugin y verificar el jar ejecutable**
  - Archivos: `pom.xml`.
  - Qué hace: agrega `maven-shade-plugin` 3.6.2 con `Main-Class: io.github.gchaldu.Main`, `finalName: gchaldu` y `<createDependencyReducedPom>false</createDependencyReducedPom>`.
  - Test: `./mvnw verify` genera `target/gchaldu.jar`; luego `java -jar target/gchaldu.jar --version`, `--help` y `--opcion-desconocida` producen los resultados esperados.
  - Criterios: **C2**, **C3**, **C4**, **C5**.

- [ ] **T10: Crear el workflow de CI**
  - Archivos: `.github/workflows/ci.yml`.
  - Qué hace: corre `./mvnw verify` con `actions/setup-java@v4`, distribución `temurin`, Java 25 y `check-latest: true`, en `ubuntu-latest`, `macos-latest` y `windows-latest`, en cada `push` y `pull_request`.
  - Test: se verifica al hacer push; localmente `./mvnw verify` sigue en verde.
  - Criterios: **C1**, **C6**.

## Cobertura de criterios

| Criterio | Tareas |
| --- | --- |
| C1 | T1, T2, T10 |
| C2 | T3, T4, T5, T6, T9 |
| C3 | T4, T5, T7, T9 |
| C4 | T4, T5, T8, T9 |
| C5 | T1, T3, T9 |
| C6 | T10 |

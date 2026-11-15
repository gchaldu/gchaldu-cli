Estado: borrador

# Spec 001: esqueleto

## Objetivo

Crear un proyecto Maven con Java 25 y Picocli que responda a `gchaldu --version` y `gchaldu --help`, con tests y CI en verde en macOS, Linux y Windows.

## Alcance

### Entra

- `pom.xml` con groupId `io.github.gchaldu`, artifactId `gchaldu`, versión `0.1.0-SNAPSHOT`, Java 25, Picocli, JUnit 6 y AssertJ.
- Maven Wrapper (`./mvnw` y `mvnw.cmd`) con Maven 3.9.16.
- Jar ejecutable en `target/gchaldu.jar`.
- Comando raíz `gchaldu` en `io.github.gchaldu.cli.GchalduCommand`, con `--version` y `--help`.
- Clase de entrada `io.github.gchaldu.Main` que arma y ejecuta la línea de comandos.
- La versión se define una sola vez en `pom.xml` y la CLI la lee desde un recurso filtrado por Maven (`app.properties`).
- Ayuda y mensajes de error en castellano.
- Workflow `.github/workflows/ci.yml` que corre `./mvnw verify` con Java 25 en `ubuntu-latest`, `macos-latest` y `windows-latest`, en cada push y en cada pull request.
- Tests de aceptación para los criterios C1 a C4.

### Queda afuera

- GraalVM Native Image y JReleaser (iteración 2).
- Los comandos `init`, `install`, `sync` y `doctor`.
- Cualquier librería de JSON.
- Manejo de excepciones de dominio (`GchalduException`) y `--verbose`; se agregan cuando haya errores esperados.

## Criterios de aceptación

- **C1**: `./mvnw verify` pasa en macOS, Linux y Windows (lo comprueba el workflow de CI).
- **C2**: `java -jar target/gchaldu.jar --version` muestra el texto `gchaldu` seguido de la versión definida en `pom.xml`, y sale con código `0`.
- **C3**: `java -jar target/gchaldu.jar --help` muestra el uso del comando en castellano, incluyendo `--version` y `--help`, y sale con código `0`.
- **C4**: `java -jar target/gchaldu.jar --opcion-desconocida` muestra un error en castellano y sale con código `2`.
- **C5**: La versión del proyecto aparece escrita una sola vez en el repositorio, en `pom.xml`.
- **C6**: El workflow `.github/workflows/ci.yml` ejecuta `./mvnw verify` con Java 25 Temurin en `ubuntu-latest`, `macos-latest` y `windows-latest`.

## Decisiones

1. **Versión inicial `0.1.0-SNAPSHOT`**. Indica que el proyecto está en desarrollo y aún no publica releases estables.
2. **Java 25 con `release` en el compilador**. Usamos `<release>25</release>` en `maven-compiler-plugin` 3.16.0 para compilar y linkear contra Java 25 de forma explícita.
3. **Picocli 4.7.7**. Es la última versión estable según Context7 y Maven Central. Soporta `--version`, `--help` y GraalVM Native Image para la iteración 2.
4. **JUnit 6.1.3**. Es la última versión GA según Context7. Requiere actualizar la constitución de "JUnit 5 y AssertJ" a "JUnit 6 y AssertJ".
5. **AssertJ 3.27.7**. Es la última versión estable 3.x según Maven Central; la 4.0.0-M1 es un milestone.
6. **Maven Shade Plugin 3.6.2** para el jar ejecutable. Genera `target/gchaldu.jar` con todas las dependencias incluidas y `Main-Class: io.github.gchaldu.Main`.
7. **Maven Surefire Plugin 3.6.1**. Ejecuta los tests de JUnit 6.
8. **Recurso filtrado `app.properties`**. Maven reemplaza `${project.version}` en `src/main/resources/app.properties`, y Picocli lo lee con un `IVersionProvider` para mostrar la versión en `--version`.
9. **Maven Wrapper con Maven 3.9.16**. Es la última versión estable de la rama 3.9.x según Maven Central.
10. **CI con Temurin y `check-latest: true`**. Garantiza que el runner use JDK 25 aunque la imagen no lo tenga cacheado.
11. **Sin `GchalduException` en esta iteración**. La constitución define ese mecanismo para errores esperados, pero `--version`, `--help` y opciones desconocidas son errores/controlados por Picocli; no hay lógica de dominio que falle.

## Preguntas

Ninguna pendiente; las definiciones fueron confirmadas por Gabriel.

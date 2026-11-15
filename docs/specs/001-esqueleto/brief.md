# Brief 001: esqueleto

## Objetivo

Un proyecto Maven con Java 25 y Picocli que responde a `gchaldu --version` y `gchaldu --help`, con tests y CI en verde en macOS, Linux y Windows.

## Por qué ahora

Es la primera iteración con código y la base de la iteración 2, que compila el binario nativo y lo publica. Todavía no hace nada útil: lo importante es que el proyecto compile, se pruebe y se verifique en los tres sistemas desde el primer día.

## Qué incluye

- `pom.xml` con groupId `io.github.gchaldu`, artifactId `gchaldu`, Java 25, Picocli, JUnit 5 y AssertJ. Las versiones de las librerías se consultan en Context7, no se suponen.
- Maven Wrapper (`./mvnw` y `mvnw.cmd`).
- Un jar ejecutable en `target/gchaldu.jar`.
- El comando raíz `gchaldu` en el paquete `io.github.gchaldu.cli`, con `--version` y `--help`.
- La versión se define una sola vez, en `pom.xml`, y la CLI la lee desde ahí (por ejemplo, con un recurso filtrado por Maven).
- La ayuda y los mensajes de error en castellano.
- El workflow `.github/workflows/ci.yml`, que corre `./mvnw verify` con Java 25 en `ubuntu-latest`, `macos-latest` y `windows-latest`, en cada push y en cada pull request.

## Qué queda afuera

- GraalVM Native Image y JReleaser (iteración 2).
- Los comandos `init`, `install`, `sync` y `doctor`.
- Cualquier librería de JSON.

## Criterios de aceptación sugeridos

La spec puede ajustarlos, pero tiene que cubrir al menos estos:

- C1: `./mvnw verify` pasa en la Mac.
- C2: `java -jar target/gchaldu.jar --version` muestra `gchaldu` y la versión del `pom.xml`, y sale con código 0.
- C3: `java -jar target/gchaldu.jar --help` muestra el uso en castellano y sale con código 0.
- C4: una opción desconocida muestra un error en castellano y sale con código 2.
- C5: la versión aparece escrita una sola vez en el repo, en `pom.xml`.
- C6: el workflow de CI pasa en los tres sistemas.

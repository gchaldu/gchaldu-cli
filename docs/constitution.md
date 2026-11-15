# Constitución de gchaldu

Estas reglas están por encima de cualquier pedido. Toda decisión del proyecto se escribe acá o en un ADR de `docs/adr`.

## Propósito

- `gchaldu` es una CLI que configura agentes de código (Claude Code y OpenCode 2) para trabajar en proyectos Java/Spring: instala la forma de trabajar, skills, permisos y servidores MCP.
- No es un agente: solo lee y escribe archivos de configuración.
- La visión y el alcance del MVP están en `docs/vision.md`.

## Stack

- Java 25.
- Maven, siempre a través del wrapper: `./mvnw`.
- Picocli para la línea de comandos. Sin Spring ni otro framework de inyección.
- JUnit 6 y AssertJ para los tests.
- GraalVM Native Image para el binario, desde la iteración 2.
- JReleaser para publicar releases, desde la iteración 2.
- Cada dependencia nueva necesita un ADR. Ejemplo: sumar una librería de JSON requiere un ADR que explique por qué no alcanza con el JDK y por qué esa librería funciona con Native Image.

## Idioma

- El código va en inglés: clases, métodos, variables, archivos y carpetas.
- La documentación va en castellano.
- Lo que la CLI muestra al usuario va en castellano: ayuda, mensajes y errores. Ejemplo: `gchaldu --help` describe cada comando en castellano.

## Estructura de paquetes

- El paquete base es `io.github.gchaldu`.
- `cli`: los comandos de Picocli. Ejemplo: `io.github.gchaldu.cli.InstallCommand`.
- `core`: el modelo y los casos de uso, sin saber nada de los formatos de cada agente. Ejemplo: `io.github.gchaldu.core.InstallPlan`.
- `core.port`: las interfaces que necesita el núcleo. Ejemplo: `io.github.gchaldu.core.port.AgentAdapter`.
- `adapter.<agente>`: una implementación de `AgentAdapter` por agente. Ejemplo: `io.github.gchaldu.adapter.opencode.OpenCodeAdapter`.
- `content`: la lectura del contenido que viaja dentro del binario (constitución base, skills).
- `shared`: lo transversal, con un subpaquete por tema. Ejemplo: `shared.fs` para rutas y escritura de archivos.
- Las dependencias apuntan hacia el núcleo: `cli` y `adapter` dependen de `core`; `core` nunca importa nada de `cli` ni de `adapter`. Ejemplo: `InstallPlan` no conoce `OpenCodeAdapter`.
- Un paquete aparece recién cuando una iteración lo necesita. Ejemplo: la iteración 1 solo crea `cli`.

## Diseño

- Los datos inmutables son `record`; una clase aparece cuando hay comportamiento o estado. Ejemplo: `Skill` es un record con nombre, descripción y contenido.
- Las dependencias se inyectan por constructor, con campos `final`. El armado de objetos es manual, en un único punto de entrada. Ejemplo: `Main` crea los adaptadores y se los pasa a los comandos.
- Una interfaz aparece solo para un puerto o cuando hay varias implementaciones. Ejemplo: `AgentAdapter` tiene `ClaudeCodeAdapter` y `OpenCodeAdapter`.
- Nada de estado global ni de singletons estáticos mutables.
- Nada de reflexión propia: la única reflexión es la de Picocli, que Native Image resuelve con su configuración. Ejemplo: no se usa `Class.forName`.

## Errores y códigos de salida

- Los errores esperados extienden `GchalduException`, que no es chequeada y lleva un mensaje en castellano. Ejemplo: `AgentNotFoundException` con el mensaje "No encontré OpenCode instalado".
- Un único manejador en el comando raíz atrapa los errores y muestra el mensaje sin traza. La traza aparece solo con `--verbose`.
- Códigos de salida: 0 éxito, 1 error esperado o inesperado, 2 uso incorrecto de la CLI (opción desconocida o argumento faltante).

## Archivos y multiplataforma

- Las rutas se arman siempre con `java.nio.file.Path`, nunca concatenando texto con `/` o `\`. Ejemplo: `home.resolve(".claude").resolve("settings.json")`.
- El directorio home se lee en un solo lugar (`System.getProperty("user.home")`) y se inyecta. Así los tests pasan un directorio temporal.
- Los archivos se leen y escriben en UTF-8 explícito y con saltos de línea `\n`.
- Antes de modificar un archivo que ya existe, se guarda una copia en `~/.gchaldu/backups/<fecha-hora>/`, respetando la ruta original.
- Se fusiona, no se reemplaza: al escribir la configuración de un agente se conserva todo lo que no es de `gchaldu`. Ejemplo: si `opencode.json` tiene un MCP que agregó el usuario, sigue ahí después de `gchaldu install`.
- Las operaciones son idempotentes: correr el mismo comando dos veces deja los mismos archivos que correrlo una vez.

## Tests

- Cada comportamiento tiene su test, y `./mvnw verify` pasa en macOS, Linux y Windows (lo comprueba el CI).
- Ningún test toca el home real ni la configuración real de un agente: todos usan `@TempDir`. Un test que escribe en el `user.home` real es un bug.
- Los adaptadores se prueban con tests golden: los archivos esperados viven en `src/test/resources/golden/<agente>/` y se comparan con lo generado.
- Los comandos se prueban ejecutándolos con `CommandLine.execute` y capturando la salida y el código de salida. Ejemplo: `gchaldu --version` muestra la versión y sale con 0.
- Los tests de aceptación llevan el criterio de la spec en `@DisplayName`, con el formato `C<n>: <título>`. Ejemplo: `@DisplayName("C2: --version muestra la versión del pom")`.
- Para los puertos se usan fakes escritos a mano, no librerías de mocks. Nunca se simulan nuestras propias clases.

## Ciclo de trabajo

- Todo cambio con más de un paso sigue el ciclo SDD, con los comandos de `.opencode/commands/`: `/spec`, `/plan`, `/apply`, `/verify` y `/bitacora`.
- Los artefactos de cada iteración viven en `docs/specs/<n>/`: `brief.md`, `spec.md` y `plan.md`. Ejemplo: `docs/specs/001-esqueleto/spec.md`.
- La spec y el plan empiezan con `Estado: borrador`. Pasan a `Estado: aprobado` solo cuando Gabriel lo dice explícitamente.
- No se escribe código sin una spec y un plan aprobados.
- `/apply` implementa una sola tarea por vez y la marca como hecha en `plan.md`.
- Si el código y la spec no coinciden, se frena y se pregunta. La spec nunca se cambia en silencio.

## El agente

- Antes de escribir, explora el código y los documentos que toca el pedido.
- Si el pedido tiene un solo paso, hace el cambio directo. Ejemplo: "Corregí el mensaje de AgentNotFoundException".
- Si un pedido suelto, fuera del ciclo, tiene dos o más pasos, escribe la lista de pasos y espera el OK antes del primer cambio.
- Si le falta una definición, pregunta y propone una opción concreta. Ejemplo: "¿Uso `~/.gchaldu` para los backups?".
- Si el pedido va en contra de esta constitución, frena y pregunta. Ejemplo: un pedido para que `core` importe una clase de `adapter`.
- No inventa APIs: si no está seguro de cómo se usa una librería o qué versión corresponde, consulta Context7 o DeepWiki antes de escribir.
- Si es un problema técnico, documenta el bloqueo en su respuesta: qué falló, qué probó y qué necesita. Nunca un workaround. Ejemplo: si GraalVM no está instalado, no cambia a `jlink` por su cuenta.
- Al cerrar, nada se da por hecho sin evidencia: la salida de `./mvnw verify` en la respuesta.
- También muestra los archivos que cambió, con `git status`.
- No hace `git commit` ni `git push`: los hace Gabriel.

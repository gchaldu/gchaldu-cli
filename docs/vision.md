# Visión y alcance del MVP

## Visión

`gchaldu` es una CLI escrita en Java que configura los agentes de código que ya usás (Claude Code y OpenCode 2) para que trabajen en proyectos Java/Spring con tus reglas, tu forma de trabajar y tus guardas de seguridad. Un solo comando, `gchaldu install`, deja a cada agente trabajando como un dev senior del equipo.

No es un agente nuevo: es un configurador. El valor está en lo que instala (constitución, skills, forma de trabajar, permisos y MCP), no en el binario.

## Problema que resuelve

Un agente recién instalado no conoce las convenciones del proyecto, se olvida entre sesiones de lo que se decidió, salta a escribir código sin plan ni tests y puede ejecutar comandos peligrosos. Hoy cada dev lo resuelve a mano y por separado en cada agente, con formatos que además cambian entre versiones (OpenCode 1 y OpenCode 2 configuran MCP y permisos de forma distinta).

Para quién es:

- Dev Java/Spring: deja de repetir instrucciones y de corregir lo mismo en cada sesión.
- Equipos: todos trabajan con las mismas reglas, sin importar qué agente use cada uno.
- Alumnos del curso: aprenden a construir herramientas de desarrollo en Java.

## Alcance del MVP

Meta: 6 semanas, con 12 a 15 horas por semana.

Comandos:

- `gchaldu init`: genera la constitución del proyecto a partir de preguntas (propósito, stack, idioma, perfil de arquitectura).
- `gchaldu install`: detecta los agentes, deja elegir cuáles configurar e instala todo.
- `gchaldu sync`: vuelve a aplicar la configuración después de actualizar `gchaldu` o su contenido.
- `gchaldu doctor`: diagnóstico de solo lectura (agentes detectados, versiones, archivos instalados).

Qué instala en cada agente:

- Forma de trabajar: explorar antes de escribir, plan y OK para pedidos de varios pasos, preguntar con una opción concreta, frenar si algo va en contra de la constitución, cerrar con evidencia.
- Skills Java/Spring según el perfil de arquitectura elegido.
- Permisos: preguntar por defecto, permitir `./mvnw` y los `git` de lectura, bloquear `git commit` y `git push`, proteger los `.env`.
- MCP: Context7 y DeepWiki.

Plataformas: Claude Code y OpenCode 2; macOS, Linux y Windows; distribución por Homebrew, Scoop y GitHub Releases.

## Perfiles de arquitectura

| Perfil | Cómo se organiza el código |
| --- | --- |
| `feature-layered` | Paquete por feature, capas cerradas `controller`, `service` y `repository`, DTOs `record`, comunicación entre features por su subpaquete `api` |
| `hexagonal` | Dominio sin JPA, puertos y adaptadores, casos de uso con nombre propio, entidades JPA en infraestructura |

Lo común a los dos perfiles: forma de trabajar, permisos, MCP y las skills que valen para cualquier arquitectura (Flyway, errores con `ProblemDetail`, tests con Testcontainers). Las skills de `gchaldu` son contenido propio del proyecto.

## Fuera del MVP

- Memoria persistente (servidor MCP propio): primera feature de la v1.1.
- Interfaz TUI con TamboUI: el MVP usa preguntas simples en consola.
- Revisión de código antes de un commit o un PR.
- Otros agentes: Cursor, Codex, Gemini CLI, Pi.
- Otros perfiles: microservicios, CQRS.
- Backend en la nube para compartir memoria en equipo.

## Arquitectura de la herramienta

- CLI (Picocli): `init`, `install`, `sync`, `doctor`.
- Núcleo: contenido embebido (constitución base y skills por perfil), plan de instalación (qué se escribe, dónde y en qué orden) y detector de agentes y versiones.
- Puerto `AgentAdapter`, con un adaptador por agente: Claude Code y OpenCode 2. Cada uno escribe instrucciones, skills, permisos y MCP en el formato de su agente.

Sumar un agente nuevo es escribir un adaptador más: el núcleo y el contenido no cambian.

## Riesgos

| Riesgo | Mitigación |
| --- | --- |
| Las skills se escriben en paralelo | Las primeras iteraciones son infraestructura; las skills entran a medida que estén listas |
| Los agentes cambian de formato | Adaptadores aislados, detección de versión y tests golden |
| GraalVM Native Image quedó fuera de Java SE en Oracle | Community Edition; plan B con `jlink` |
| Sin una máquina Windows propia | Runners de Windows en GitHub Actions y una VM |
| El código lo generan modelos de OpenCode Go | Tareas chicas, tests obligatorios y revisión en cada iteración |

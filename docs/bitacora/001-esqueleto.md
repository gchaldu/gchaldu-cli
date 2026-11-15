# 001 Esqueleto

- Fecha: 2026-11-15
- Horas dedicadas: 3

## Qué pedimos

Crear el esqueleto del proyecto Maven con Java 25 y Picocli: que la CLI responda a `gchaldu --version` y `gchaldu --help`, con tests de aceptación y CI en macOS, Linux y Windows. Ver `docs/specs/001-esqueleto/spec.md` y `docs/specs/001-esqueleto/plan.md`.

## Qué salió bien

- El pipeline de especificación quedó claro desde el arranque: primero se redactó y aprobó la spec, luego el plan, y recién después se escribió código.
- Test primero. Cada criterio de aceptación (C2 a C4) tiene su test con `CommandLine.execute`, y los tests no hardcodean la versión: la leen de la propiedad de sistema `project.version` que inyecta Surefire. La revisión del plan encontró que los tests se escribieron después del código y que el test de T4 probaba una configuración que la app no usaba en producción; ambos problemas se corrigieron.
- La localización al castellano quedó prolija: encabezados, descripción de opciones, `Uso:` y el mensaje de opción desconocida.
- El recurso filtrado `app.properties` con `${project.version}` permite que la versión viva solo en `pom.xml` (C5).
- El jar ejecutable con Shade se genera como `target/gchaldu.jar` y pasó las verificaciones manuales de `--version`, `--help` y opción desconocida.
- El workflow de CI corre `./mvnw verify` en los tres sistemas operativos con Temurin JDK 25 y `check-latest: true`.
- El CI pasó en verde al primer intento en `ubuntu-latest`, `macos-latest` y `windows-latest`.

## Qué salió mal y cómo lo resolvimos

### Entorno y repo

- El Finder ocultó `.opencode/` y `.gitignore` al descomprimir el zip; se resolvió con `Cmd + Shift + .` para mostrar archivos ocultos.
- El primer repo se llamó `gchaldu`, igual que el usuario de GitHub, y ese repo se muestra en el perfil; se renombró a `gchaldu-cli`.
- Gentle AI instalado global en OpenCode generó `.atl/` y carga su persona en todas las sesiones. Es el mismo problema de convivencia entre configuración global y de proyecto que `gchaldu` va a tener que resolver.

### Spec y plan

- OpenCode encontró que JUnit 6 ya era GA (el brief decía JUnit 5) y que `actions/setup-java` va por la v6.
- La revisión de la spec encontró: C1 y C6 superpuestos, Picocli en inglés por defecto, una regla de ADR ambigua en la constitución, el `dependency-reduced-pom.xml` de Shade y que no estaba definido cómo verificar C2 a C4.
- La revisión del plan encontró: T1 y T2 en un orden que no funcionaba, tests escritos después del código, la versión escrita en los tests (rompía C5) y el shell de Windows en el CI.
- El plan menciona `GchalduVersionProvider` en el texto de T2 pero no en su lista de archivos.
- C5 dice "una sola vez en el repositorio", pero la versión también aparece en la spec y el plan como documentación. En próximas specs, acotar a "código y configuración del build".

### Tareas

- **T2**: se agregó `maven-resources-plugin` 3.5.0 solo para fijar `propertiesEncoding`, que no se puede configurar desde `<properties>`. El arquitecto pidió quitarlo por error y OpenCode había anticipado el warning.
- **T2, revisión**: el test era más permisivo que C2 (dos `contains` separados), reemplazaba `System.out` global y leía `app.properties` en ISO-8859-1. Se corrigió con `setOut` de Picocli y un `Reader` en UTF-8.
- **T3**: se descartó `mixinStandardHelpOptions` porque sus descripciones vienen en inglés y no se pueden traducir.
- **T4**: el handler quedó registrado en el test y no en producción, así que el test probaba una configuración que la app no usaba. Se resolvió con `GchalduCommand.newCommandLine()`, que usan los tests y `Main`.
- **T5**: OpenCode aceptó un warning de Shade por ser "común"; se rechazó para mantener cero warnings y se resolvió con un filtro de `META-INF/MANIFEST.MF` para picocli. También quedó un import sin usar en `Main`.
- **T5**: se verificó a mano el jar real. `gchaldu` sin argumentos muestra un stack trace en inglés con código `1`; queda para la iteración 2, antes de publicar el binario.
- **T6**: OpenCode propuso `actions/checkout@v4` cuando ya existía la v7.

### Verificación y CI

- `/verify` dio dos hallazgos falsos: que no existía `.opencode/commands/` y una T7 para correr el CI, que ya estaba en verde.
- El CI corre dos veces por commit en el PR (`push` y `pull_request`).
- OpenCode mostró diffs con errores (un archivo `GchalduCommandRenderer.java` inexistente y cierres del pom duplicados). La revisión en GitHub confirmó que el código estaba bien.

## Prompts o ajustes que funcionaron

- Pedir la spec antes del código y no escribir código hasta que ambos documentos (`spec.md` y `plan.md`) estuvieran aprobados mantuvo el foco; tener la spec aprobada dio un criterio contra el cual revisar cada ajuste.
- La instrucción de que cada tarea use "test primero" hizo que los tests guiaran la implementación y que los criterios de aceptación fueran verificables de inmediato.
- Los pedidos de ajuste con el formato "Ajustes de revisión sobre Tn, sin tocar el plan", terminando con "corré `./mvnw verify` y mostrame el diff", permitieron corregir sin reabrir la spec ni el plan.

## Decisiones tomadas (ADR)

- Ninguna ADR separada: todas las decisiones de esta iteración quedaron registradas en la sección "Decisiones" de `docs/specs/001-esqueleto/spec.md`.
- Destacadas: uso de JUnit 6.1.3 en lugar de JUnit 5, Java 25 con `<release>25</release>`, Picocli 4.7.7, Maven Shade Plugin 3.6.2, y verificación dual de C2 a C4 (tests + ejecución real del jar en `/verify`).

## Aprendizajes

- El modelo tiende a proponer las versiones que conoce y no las actuales: verificar siempre versiones de dependencias y actions.
- Los informes del agente también se verifican; `/verify` dio dos hallazgos falsos.
- Revisar los commits en GitHub y no solo el resumen del agente.
- Corregir en el momento los patrones de la primera tarea (por ejemplo, el test de T2), porque las tareas siguientes los copian.
- Avanzar rápido sin entender la librería es un riesgo: antes de la iteración 2, repasar el código de Picocli generado.
- El problema de Gentle AI global (`~/.config/opencode/AGENTS.md`) anticipa el mismo escenario que `gchaldu` tendrá que resolver: convivencia entre configuración global del agente y configuración por proyecto.

## Idea de clase para el curso

Cómo revisar el código que genera un agente cuando no conocés la librería. Gabriel no había usado Picocli y avanzó rápido sin entender del todo lo generado; aun así, la revisión encontró los problemas con principios generales (estado global en tests, configuración en un solo lugar, cero warnings, versiones actuales) y no con conocimiento de Picocli.


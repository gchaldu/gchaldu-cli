# gchaldu

CLI en Java 25 que configura agentes de código (Claude Code y OpenCode 2) para proyectos Java/Spring.

Las reglas del proyecto están en `docs/constitution.md` y están por encima de cualquier pedido. Si no la tenés cargada, leela antes de empezar.

Comandos:
- Verificar el código: `./mvnw verify`; mostrá el resultado.
- Probar la CLI (desde la iteración 1): `java -jar target/gchaldu.jar --version`.

Cómo trabajamos: ciclo SDD con `/spec`, `/plan`, `/apply`, `/verify` y `/bitacora`, descrito en la sección "Ciclo de trabajo" de `docs/constitution.md`. Qué hace el agente antes de escribir, cuando algo lo frena y al cerrar está en la sección "El agente".

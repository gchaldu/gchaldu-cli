# 0003 SDD liviano propio con comandos de OpenCode

- Estado: aceptado
- Fecha: 2026-11-13

## Contexto

`gchaldu` se desarrolla con OpenCode y Gabriel no escribe código: especifica y revisa. Hace falta un flujo que obligue a planificar antes de codear y que deje registro de cada paso. Existen herramientas armadas (OpenSpec, el flujo SDD de Gentle AI), pero suman dependencias y esconden decisiones que queremos entender.

## Decisión

Usamos un SDD liviano propio, en Markdown, con cinco comandos de OpenCode en `.opencode/commands/`: `/spec`, `/plan`, `/apply`, `/verify` y `/bitacora`. La spec y el plan necesitan el OK explícito de Gabriel antes de escribir código.

## Consecuencias

- Sin dependencias externas y con cada paso a la vista.
- Los comandos son material directo para el curso.
- Estos mismos comandos pueden empaquetarse después como contenido de `gchaldu`.
- Si el flujo resulta incómodo, se ajustan los comandos y se registra en un ADR nuevo.

# 0001 Constitución corta; el cómo se hace va a skills

- Estado: aceptado
- Fecha: 2026-11-13

## Contexto

Los agentes cargan la constitución en cada sesión, así que todo lo que vive ahí gasta contexto siempre, se use o no. Las skills, en cambio, se cargan solo cuando la tarea las necesita. En el curso de desarrollo con OpenCode y Spring ya se aplicó este criterio: las convenciones de los tests van a la skill de testing y no a la constitución.

## Decisión

La constitución contiene solo lo innegociable: propósito, stack, estructura, reglas de diseño y la forma de trabajar del agente. El detalle de cómo se hace cada cosa (cómo se escribe un test, cómo se arma un adaptador) va a skills.

Aplica en dos lugares: en este repo y en el contenido que `gchaldu` instala en los proyectos de los usuarios.

## Consecuencias

- Menos contexto gastado en cada sesión y reglas más fáciles de leer.
- Hay que decidir, para cada regla nueva, si es innegociable o es un cómo.
- Si una skill no se carga cuando debería, el agente no sigue esa convención: se detecta en `/verify`.

---
description: Escribe la spec de una iteración a partir de su brief
---
Iteración: $ARGUMENTS

1. Leé `docs/specs/$ARGUMENTS/brief.md`, `docs/constitution.md` y `docs/vision.md`.
2. Explorá el código y los documentos que toca el brief.
3. Escribí `docs/specs/$ARGUMENTS/spec.md` con esta estructura:
   - Primera línea: `Estado: borrador`.
   - Objetivo: una o dos oraciones.
   - Alcance: qué entra y qué queda afuera.
   - Criterios de aceptación: numerados `C1`, `C2`, etc. Cada uno se verifica con un test o con un comando concreto.
   - Decisiones: las que tomás y por qué. Si alguna merece un ADR, indicalo.
   - Preguntas: lo que no está definido, cada una con una opción concreta propuesta.
4. No escribas código ni tests.
5. Si hay preguntas, hacelas primero y esperá las respuestas.
6. Al terminar, mostrá un resumen de la spec y esperá el OK de Gabriel. Cuando lo dé, cambiá la primera línea a `Estado: aprobado`.

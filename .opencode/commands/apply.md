---
description: Implementa la siguiente tarea pendiente del plan
---
Iteración: $ARGUMENTS

1. Leé `docs/specs/$ARGUMENTS/plan.md`. Si su primera línea no es `Estado: aprobado`, frená y avisale a Gabriel.
2. Tomá la primera tarea sin marcar (`- [ ]`). Solo esa.
3. Escribí primero el test y después el código, respetando `docs/constitution.md`.
4. Corré `./mvnw verify`. Si falla, corregí hasta que pase o documentá el bloqueo como indica la sección "El agente" de la constitución.
5. Marcá la tarea como hecha (`- [x]`) en `plan.md`.
6. Mostrá la tarea terminada, la salida resumida de `./mvnw verify` y `git status`.
7. No sigas con la próxima tarea: esperá a que Gabriel revise y vuelva a ejecutar `/apply`.

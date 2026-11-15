---
description: Verifica la iteración contra sus criterios de aceptación
---
Iteración: $ARGUMENTS

1. Corré `./mvnw verify` y mostrá el resultado.
2. Leé `docs/specs/$ARGUMENTS/spec.md`. Para cada criterio `C<n>`, indicá si se cumple y con qué evidencia: el test que lo prueba o el comando que corriste, con su salida.
3. Revisá que el código respete `docs/constitution.md`. Listá todo lo que vaya en contra, con archivo y línea.
4. Revisá que todas las tareas de `plan.md` estén marcadas.
5. Mostrá `git status`.
6. No corrijas nada. Si algo no se cumple, proponé la tarea que habría que agregar al plan.

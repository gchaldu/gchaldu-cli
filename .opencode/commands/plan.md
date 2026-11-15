---
description: Divide una spec aprobada en tareas chicas, cada una con su test
---
Iteración: $ARGUMENTS

1. Leé `docs/specs/$ARGUMENTS/spec.md`. Si su primera línea no es `Estado: aprobado`, frená y avisale a Gabriel.
2. Escribí `docs/specs/$ARGUMENTS/plan.md`:
   - Primera línea: `Estado: borrador`.
   - Una lista de tareas con el formato `- [ ] T<n>: <título>`.
   - Debajo de cada tarea: qué archivos cambia, qué test la prueba y qué criterios `C<n>` cubre.
3. Cada tarea es chica: un cambio que se revisa en pocos minutos y que deja `./mvnw verify` en verde.
4. Ordená las tareas por dependencia. Todos los criterios de la spec tienen que quedar cubiertos por alguna tarea.
5. No escribas código.
6. Mostrá el plan y esperá el OK de Gabriel. Cuando lo dé, cambiá la primera línea a `Estado: aprobado`.

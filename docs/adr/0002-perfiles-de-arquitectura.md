# 0002 Perfiles de arquitectura

- Estado: aceptado
- Fecha: 2026-11-13

## Contexto

Los proyectos Java/Spring no se organizan todos igual. Las reglas del curso de desarrollo con OpenCode y Spring son para un proyecto dividido por feature con capas adentro, y el curso de Patrones de Arquitectura usa hexagonal. Un único set de skills no sirve para los dos.

## Decisión

`gchaldu` instala contenido común a todos los proyectos más las skills de un perfil de arquitectura. El MVP trae dos perfiles: `feature-layered` y `hexagonal`. El perfil se elige en `gchaldu init` y queda guardado en el proyecto, así `gchaldu sync` sabe qué instalar.

## Consecuencias

- Sumar una arquitectura nueva es sumar un perfil, sin tocar el núcleo.
- El contenido de dos perfiles es más trabajo de escritura que el de uno.
- Las skills comunes tienen que ser realmente neutrales: no pueden asumir capas ni puertos.

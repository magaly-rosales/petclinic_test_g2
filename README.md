# Spring Boot : Application PetClinic

## Grupo 2 - Pruebas unitarias para VetService 

### Integrantes y pruebas realizadas

| Integrante | Rol | Rama | Pruebas |
|---|---|---|---|
| Ana Merino Ramos | A - CRUD básico | `Ana-Merino-Ramos` | `testCreateVet`, `testUpdateVet`, `testFindVetById` |
| Magaly Rosales | B - Soft delete y reactivación | `feature/magaly-vet-soft-delete` | `testDeactivateVet`, `testReactivateVet`, `testFindActiveVets` |
| Jeff Amaro | C - Casos de error y filtros | `integrante-c-casos-error` | `testFindVetById_NotFound`, `testDeactivateVet_NotFound`, `testFindInactiveVets` |

### Flujo de trabajo
- Cada integrante trabajó en su propia rama.
- Cada rama se integró a `main` mediante un Pull Request aprobado por otro integrante.
- La rama `main` está protegida (requiere 1 aprobación antes del merge).

### Conflictos resueltos
- **PR de Magaly (B):** fue el primero en integrarse a `main`, por eso no tuvo conflictos.
- **PR de Ana (A):** tuvo conflicto en `VetServiceTest.java` al integrar los cambios de `main` (pruebas de Magaly y Jeff); se resolvió conservando las pruebas de los tres integrantes.
- **PR de Jeff (C):** fue el segundo en integrarse a main. Presentó conflictos de merge en el archivo VetServiceTest.java con el código de Magaly (B). Se resolvieron mediante la herramienta visual del IDE, manteniendo las pruebas de ambos integrantes y corrigiendo las importaciones y llaves faltantes.

### Ejecutar las pruebas
```
./mvnw test
```
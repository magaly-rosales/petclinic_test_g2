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

---

---

## Lab 12 - Pruebas de integración para VetController (MockMvc + H2 + Mockito)

### Integrantes y pruebas realizadas

Cada prueba está en `VetControllerTest` (integración real con H2) y en `VetControllerMockitoTest` (servicio mockeado).

| Integrante | Rol | Rama | Pruebas |
|---|---|---|---|
| Ana Merino Ramos | A - Listado y consulta | `AnaMerino_PruebasIntegracion` | `testFindAllVets`, `testFindVetOK`, `testFindVetKO` |
| Magaly Rosales | B - Creación y soft delete | `feature/magalylabo12` | `testCreateVet`, `testDeactivateVet`, `testReactivateVet` |
| Jeff Amaro | C - Filtros y borrado | `integrante-c-final` | `testFindActiveVets`, `testFindInactiveVets`, `testDeleteVetKO` |

### Funcionalidad agregada
- `VetController` con los endpoints `/vets`.
- Métodos `findAll` y `delete` en `VetService`.

### Conflictos resueltos
- **PR de Ana (A):** fue la primera en integrar sus pruebas, sin conflictos.
- **PR de Magaly (B):** conflicto en `VetControllerTest.java` y `VetControllerMockitoTest.java` porque Ana había creado los mismos archivos; se resolvió conservando las pruebas de ambas.
- **PR de Jeff (C):** conflicto en `VetControllerTest.java` y `VetControllerMockitoTest.java` al integrar los cambios de `main`; se resolvió conservando las pruebas de los tres integrantes.

### Ejecutar las pruebas
```
./mvnw clean test -Dspring.profiles.active=h2
```
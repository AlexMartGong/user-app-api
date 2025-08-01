# API de Paginación - Documentación

## Resumen
Se ha implementado la funcionalidad de paginación en la API de usuarios para permitir la recuperación eficiente de grandes conjuntos de datos.

## Endpoints Disponibles

### 1. Obtener Usuarios con Paginación
```
GET /api/users
```

#### Parámetros de Consulta (Query Parameters):
- `page` (opcional, default: 0): Número de página (base 0)
- `size` (opcional, default: 10): Tamaño de página (número de elementos por página)
- `sortBy` (opcional, default: "id"): Campo por el cual ordenar
- `sortDir` (opcional, default: "asc"): Dirección del ordenamiento ("asc" o "desc")

#### Ejemplos de Uso:

##### Obtener la primera página con valores por defecto:
```
GET /api/users
```

##### Obtener la segunda página con 5 elementos:
```
GET /api/users?page=1&size=5
```

##### Ordenar por nombre en orden descendente:
```
GET /api/users?sortBy=firstName&sortDir=desc
```

##### Combinación de parámetros:
```
GET /api/users?page=0&size=20&sortBy=email&sortDir=asc
```

#### Respuesta de Ejemplo:
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "admin": false
    },
    {
      "id": 2,
      "username": "jane_smith",
      "firstName": "Jane",
      "lastName": "Smith",
      "email": "jane@example.com",
      "admin": true
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 25,
  "totalPages": 3,
  "first": true,
  "last": false,
  "empty": false
}
```

### 2. Obtener Todos los Usuarios (Sin Paginación)
```
GET /api/users/all
```

Este endpoint devuelve todos los usuarios sin paginación, útil para casos donde necesites la lista completa.

## Estructura de Respuesta Paginada

### Campos de la Respuesta:
- `content`: Array de elementos (usuarios) en la página actual
- `page`: Número de página actual (base 0)
- `size`: Tamaño de página configurado
- `totalElements`: Número total de elementos en la base de datos
- `totalPages`: Número total de páginas
- `first`: Boolean indicando si es la primera página
- `last`: Boolean indicando si es la última página
- `empty`: Boolean indicando si la página está vacía

## Campos Disponibles para Ordenamiento

Puedes ordenar por cualquier campo del UserDTO:
- `id`
- `username`
- `firstName`
- `lastName`
- `email`

## Consideraciones de Rendimiento

1. **Tamaño de Página**: Se recomienda mantener el tamaño de página entre 10-50 elementos para un rendimiento óptimo
2. **Índices**: Asegúrate de que los campos utilizados para ordenamiento tengan índices en la base de datos
3. **Caché**: Para listas que no cambian frecuentemente, considera implementar caché

## Manejo de Errores

- Si la página solicitada no existe, se devuelve una respuesta vacía con `empty: true`
- Los parámetros inválidos se manejan con valores por defecto
- Si no hay usuarios, se devuelve un estado HTTP 204 (No Content)

## Ejemplos de Implementación en Frontend

### JavaScript/Fetch:
```javascript
async function getUsers(page = 0, size = 10, sortBy = 'id', sortDir = 'asc') {
  const response = await fetch(
    `/api/users?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`
  );
  
  if (response.status === 204) {
    return { content: [], empty: true };
  }
  
  return await response.json();
}
```

### Navegación de Páginas:
```javascript
function buildPagination(pagedResponse) {
  const { page, totalPages, first, last } = pagedResponse;
  
  // Página anterior
  const prevPage = first ? null : page - 1;
  
  // Página siguiente
  const nextPage = last ? null : page + 1;
  
  return {
    currentPage: page + 1, // Para mostrar base 1 al usuario
    totalPages,
    prevPage,
    nextPage,
    canGoPrev: !first,
    canGoNext: !last
  };
}
```

## Cambios Realizados

### 1. Repository (UserRepository.java):
- Extendido `PagingAndSortingRepository<User, Long>`
- Agregado método `findAll(Pageable pageable)`

### 2. Service (UserService.java & UserServiceImpl.java):
- Agregado método `findAll(Pageable pageable)` que retorna `PagedResponse<UserDTO>`
- Implementación que convierte `Page<User>` a `PagedResponse<UserDTO>`

### 3. Controller (UserController.java):
- Modificado endpoint principal `/api/users` para soportar paginación
- Agregado endpoint `/api/users/all` para obtener todos los usuarios
- Parámetros de consulta para paginación y ordenamiento

### 4. DTO (PagedResponse.java):
- Nueva clase para encapsular respuestas paginadas
- Incluye metadata de paginación

## Próximos Pasos Recomendados

1. **Filtros**: Implementar filtros por campos específicos
2. **Búsqueda**: Agregar funcionalidad de búsqueda por texto
3. **Cache**: Implementar caché para consultas frecuentes
4. **Validación**: Agregar validaciones para parámetros de paginación
5. **Documentación API**: Integrar con Swagger/OpenAPI para documentación automática

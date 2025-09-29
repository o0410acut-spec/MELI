# Inventory Service — Optimización de Sistema Distribuido

## 📌 Resumen

Este proyecto es un prototipo de un **sistema distribuido de gestión de inventario** desarrollado en **Java 17 / Spring Boot** con arquitectura hexagonal.  
Demuestra mejoras en:

- **Consistencia**: uso del **patrón Outbox + Kafka** con idempotencia.
- **Latencia reducida**: flujo de eventos en tiempo real.
- **Observabilidad**: métricas vía Micrometer + Prometheus + Grafana.
- **Seguridad básica**: endpoints actuator limitados al entorno local.

## ⚙️ Stack técnico

- **Java 17 / Spring Boot 3**
- **H2 Database** (memoria) + JPA
- **Kafka + Zookeeper** (mensajería)
- **Prometheus + Grafana + Alertmanager** (monitoring)
- **JUnit 5 + Mockito** (tests)
- **Docker Compose** para orquestación

## 🚀 Cómo ejecutar localmente

### 1. Construcción del backend

```bash
# Compilar el proyecto
mvn clean package -DskipTests

# Verificar la construcción
java -jar target/inventory-service-0.0.1-SNAPSHOT.jar --version
```

### 2. Iniciar Infraestructura

```bash
# Iniciar todos los servicios (Kafka + Monitoreo)
docker-compose -f docker-compose.yml -f docs/docker-compose-monitoring.yml up -d

# Verificar que todos los servicios están corriendo
docker-compose ps
```

Servicios disponibles:

- Kafka: localhost:9092
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (admin/admin)
  - Dashboard preconfigurado para métricas de inventario
  - Alertas configuradas para stock bajo y errores
- AlertManager: http://localhost:9093

### 3. Iniciar la Aplicación

```bash
./mvnw spring-boot:run
```

## API Documentation

La documentación OpenAPI está disponible en:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

### Ejemplos de Uso

#### 1. Crear Reserva

```bash
curl --location 'http://localhost:8080/inventory/reserve' \
--header 'Content-Type: application/json' \
--data '{
  "storeId": "store-001",
  "productId": "sku-100",
  "quantity": 2,
  "transactionId": "tx-123"
}'

# Respuesta Exitosa:
{
  "reservationId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "PENDING",
  "expiresAt": "2024-01-24T12:34:56.789Z"
}
```

#### 2. Confirmar Reserva

```bash
curl --location --request POST 'http://localhost:8080/inventory/commit?reservationId=550e8400-e29b-41d4-a716-446655440000'

# Respuesta: 200 OK
```

#### 3. Liberar Reserva

```bash
curl --location --request POST 'http://localhost:8080/inventory/release' \
--header 'Content-Type: application/json' \
--data '{
  "reservationId": "550e8400-e29b-41d4-a716-446655440000",
  "reason": "customer_cancelled"
}'

# Respuesta: 200 OK
```

#### 4. Consultar Inventario

```bash
curl --location 'http://localhost:8080/inventory'

# Respuesta:
[
  {
    "storeId": "store-001",
    "productId": "sku-100",
    "totalQuantity": 100,
    "reservedQuantity": 2,
    "available": 98
  }
]
```

### Manejo de Errores

#### Stock Insuficiente

```json
{
  "message": "Insufficient stock: Available: 5, Requested: 10"
}
```

#### Producto No Encontrado

```json
{
  "message": "Product not found"
}
```

## Monitoreo

### Métricas Disponibles

- `inventory_reservations_total`: Total de reservas creadas
- `inventory_reservation_duration_seconds`: Duración de las reservas
- `inventory_stock_level`: Nivel actual de stock por producto

### Logs Importantes

```log
2024-01-24 12:34:56 INFO  Created reservation id=xxx, transactionId=yyy
2024-01-24 12:34:57 INFO  Committed reservation id=xxx
2024-01-24 12:34:58 WARN  Insufficient stock for reservation
```

## Nota Importante sobre Implementación

> **Nota**: La entidad Product aquí es una implementación de ejemplo para la demo/QA. En un sistema real, el Product Service centralizado provee esta información y este servicio sólo almacena productId.

## Trade-offs y Decisiones

1. **Consistencia Eventual vs Fuerte**:

   - Se eligió consistencia eventual para el checkout usando el patrón Outbox
   - Beneficio: mejor disponibilidad y escalabilidad
   - Costo: ventana de inconsistencia temporal

2. **Optimistic Locking**:

   - Versioning optimista en lugar de locks pesimistas
   - Beneficio: mejor concurrencia y throughput
   - Costo: retries ocasionales

3. **In-Memory Database**:

   - H2 para prototipado rápido
   - Trade-off: simplicidad vs durabilidad

4. **Implementación de Productos**:
   - Se incluye entidad Product completa solo para demostración
   - En producción: solo referencias a productId
   - Datos de productos se obtendrían vía Product Service

## Limitaciones y Próximos Pasos

### Limitaciones Actuales

- Sin persistencia durable (H2 in-memory)
- Implementación simplificada de productos (sin integración con Product Service)

### Próximos Pasos

1. Migrar a PostgreSQL
2. Añadir circuit breakers
3. Añadir rate limiting
4. Logging estructurado
5. Healthchecks más robustos
6. Integrar con Product Service centralizado
7. Implementar cache de datos de productos

## Documentación Técnica

### Diagramas de Arquitectura

Los diagramas se generan usando [Mermaid CLI](https://github.com/mermaid-js/mermaid-cli). Para generar los diagramas:

1. Instalar mermaid-cli globalmente:

```bash
npm install -g @mermaid-js/mermaid-cli
```

2. Generar el diagrama:

```bash
# Desde el directorio docs/
mmdc -i architecture-diagram.mmd -o architecture-diagram.png --scale 4
```

El diagrama de arquitectura muestra:

- Flujo de requests HTTP
- Procesamiento de comandos
- Publicación de eventos vía Outbox pattern
- Job de expiración de reservas

![Diagrama de Arquitectura](docs/architecture-diagram.png)
![Diagrama de Secuencia](docs/sequence-diagram.png) - Flujos de interacción principales
![Diagrama de Paquetes](doc/packages-diagram.png)

Los otros diagramas incluyen:

- [Event Flow](docs/event-flow.png) - Flujo de eventos entre servicios
- [Data Model](docs/data-model.png) - Modelo de datos y relaciones

## Prompts IA

Los prompts utilizados para el desarrollo están documentados en [prompts-used.txt](./prompts-used.txt)

## 🧪 Pruebas

### Ejecutar Tests

```bash
# Ejecutar todas las pruebas
./mvnw test

# Ejecutar pruebas unitarias específicas
./mvnw test -Dtest=InventoryUseCaseTest

# Ejecutar pruebas de integración
./mvnw test -Dtest=*IntegrationTest

# Ejecutar pruebas de carga
./mvnw test -Dtest=*LoadTest

# Generar reporte de cobertura
./mvnw test jacoco:report
```

### Ver Resultados

- Reporte JUnit: `target/surefire-reports/`
- Cobertura de código: `target/site/jacoco/index.html`

### Tipos de Pruebas

1. **Unitarias** (`*Test.java`)

   - Casos de uso aislados
   - Mocks de dependencias
   - Validación de lógica de negocio

2. **Integración** (`*IntegrationTest.java`)

   - Flujos completos
   - Base de datos en memoria
   - Validación de transacciones

3. **Carga** (`*LoadTest.java`)

   - Concurrencia
   - Optimistic locking
   - Rendimiento bajo estrés

4. **API** (`*ApiTest.java`)
   - Endpoints REST
   - Validación de requests/responses
   - Manejo de errores

## 📄 Ejemplos de Requests (REST Client)

Puedes usar estos ejemplos con el plugin REST Client de VS Code o importarlos en Postman:

```http
### Crear un producto
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "Laptop Dell",
  "sku": "sku-001",
  "price": 1200.0,
  "stock": 10
}

###

### Listar productos
GET http://localhost:8080/api/v1/products
Accept: application/json

###

### Crear inventario (opcional, si API lo expone)
POST http://localhost:8080/api/v1/inventory
Content-Type: application/json

{
  "storeId": "store-1",
  "productId": "sku-001",
  "stock": 5
}

###

### Reservar producto
POST http://localhost:8080/api/v1/inventory/reserve
Content-Type: application/json

{
  "storeId": "store-1",
  "productId": "sku-001",
  "quantity": 2
}

###

### Verificar health
GET http://localhost:8080/actuator/health

###

### Ver métricas Prometheus
GET http://localhost:8080/actuator/prometheus
```

También se puede encontrar estos ejemplos en el archivo [docs/sample-requests.http](docs/sample-requests.http).

## 📬 Colección Postman

Se incluye una colección de Postman con todos los endpoints y ejemplos:
[MELI.postman_collection.json](MELI.postman_collection.json)

Para importar:

1. Abrir Postman
2. Clic en "Import"
3. Seleccionar el archivo MELI.postman_collection.json
4. Los endpoints estarán disponibles en una nueva colección

# Weather History API

API REST que consulta el clima actual de cualquier ciudad usando Open-Meteo y guarda cada consulta en su propia base de datos, para después poder sacar promedios, máximas y mínimas — cosas que la API externa no ofrece.

## Stack

Java 21, Spring Boot, Spring Data JPA, PostgreSQL, RestTemplate.

## Cómo funciona

Open-Meteo no busca por nombre de ciudad, trabaja con coordenadas. Así que cada consulta encadena dos llamadas:

1. **Geocoding** — nombre de la ciudad → latitud y longitud
2. **Forecast** — esas coordenadas → clima actual

Después arma un registro propio con los datos de ambas respuestas y lo guarda.

## Cómo correrlo

### Con Docker 
Necesitás Docker instalado.

```bash
docker-compose up --build
```

Levanta la API y Postgres juntos, y crea las tablas solas la primera vez. Queda en `http://localhost:8080`. Los datos persisten entre reinicios (`docker-compose down` / `up`), no se pierden.

### Sin Docker

Necesitás PostgreSQL corriendo y una base llamada `weatherhistory`. La conexión se configura en `application.properties` con las variables de entorno.
Las tablas se crean solas al arrancar (`spring.jpa.hibernate.ddl-auto=update`).

```bash
mvn spring-boot:run
```

## Endpoints

**Consultar y guardar el clima de una ciudad:**
```
POST /clima/consultar?ciudad=Rosario
POST /clima/consultar?ciudad=Surabaya&pais=Indonesia
POST /clima/consultar?ciudad=Cordoba&provincia=Cordoba
POST /clima/consultar?ciudad=Rawson&provincia=Chubut&pais=Argentina
```
`provincia` y `pais` son opcionales, sirven para desambiguar ciudades con el mismo nombre. Devuelve 201 con el registro creado, o 404 si no se encuentra la ciudad.

**Historial de una ciudad:**
```
GET /clima/historial?ciudad=Rosario
```

**Promedio de temperatura de los últimos N días:**
```
GET /clima/promedio?ciudad=Rosario&dias=7
```
`dias` por defecto es 7.

**Temperatura máxima y mínima registrada:**
```
GET /clima/maxima
GET /clima/maxima?ciudad=Rosario
GET /clima/minima?ciudad=Rosario
```
Sin el parámetro `ciudad`, busca en todos los registros.

**Ciudades consultadas hasta ahora:**
```
GET /clima/ciudades_registradas
```

## Qué guarda de cada consulta

Ciudad, provincia, país, coordenadas(longitud y latitud), temperatura, sensación térmica, humedad, velocidad y dirección del viento, cobertura de nubes y la fecha de la consulta.

## Estructura

```
controller/   endpoints REST
service/      lógica y llamadas a Open-Meteo
repository/   acceso a datos (Spring Data JPA)
model/        entidad Clima
dto/          mapeo de las respuestas de Open-Meteo
exception/    excepciones propias y exception handler
config/       bean de RestTemplate
```

## Ejemplos, por si usás curl

Consultar y guardar el clima de una ciudad:
```bash
curl -X POST "http://localhost:8080/clima/consultar?ciudad=Rosario"
```
```json
{"ciudad":"Rosario","provincia":"Santa Fe","pais":"Argentina","latitud":-32.9468,"longitud":-60.6393,"temperatura":18.3,"velocidadViento":12.1,"humedad":67,"direccionViento":"210","sensacionTermica":17.9,"coberturaNubes":40,"fechaConsulta":"2026-09-10T14:20:11","id":1}
```

Ciudad que no existe:
```bash
curl -i -X POST "http://localhost:8080/clima/consultar?ciudad=Xyzabc123"
```
```json
{"message":"No se encontro la ciudad Xyzabc123.","status":404,"error":"Not Found"}
```

Promedio de los últimos 7 días:
```bash
curl -X GET "http://localhost:8080/clima/promedio?ciudad=Rosario"
```

## Pendiente

- Tests
- Deploy

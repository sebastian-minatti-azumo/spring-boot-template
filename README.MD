
# Spring Boot application

Includes web-server on port 9080 with `/users` and `/projects` endpoints exposed.  
Supports CRUD set of operations and Read with pagination

## Prerequisites:
- Maven 3
- JDK 17

## How to build:
    mvn clean install

#### Build Docker image with application inside:
    docker build ./ -t backend-template-app

## Start application (in-memory DB H2) using Maven:
    mvn spring-boot:run -Dspring-boot.run.arguments="\
    --spring.datasource.url=jdbc:h2:mem:testdb \
    --spring.datasource.username=sa \
    --spring.datasource.password=password \
    --spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect \
    --spring.datasource.driver-class-name=org.h2.Driver"

## Start application in Docker container (Postgres DB in Docker):
    docker-compose up

## Start application in Docker container (Postgres DB in Docker) with rebuild service image:
    docker-compose up --build --force-recreate --no-deps

## Swagger documentation:
    http://localhost:9080/swagger-ui.html

## Useful CURL commands

### New user addition:
```
curl -X 'POST' \
  'http://localhost:9080/api/v1/users' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "John",
  "email": "Doe"
}'
```

### Get existing user:
```
curl -X 'GET' \
  'http://localhost:9080/api/v1/users/1' \
  -H 'accept: */*'
```

### Update existing user:
```
curl -X 'PATCH' \
  'http://localhost:9080/api/v1/users/1' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "John",
  "email": "john@gmail.com"
}'
```

### Get list of users with pagination support:
```
curl -X 'GET' \
  'http://localhost:9080/api/v1/users?pageNo=0&pageSize=10' \
  -H 'accept: */*'
```

### Deletion of user:
```
curl -X 'DELETE' \
  'http://localhost:9080/api/v1/users/4' \
  -H 'accept: */*'
```

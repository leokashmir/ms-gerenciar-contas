# Micro Service - Gerenciar Contas


### Sobre o Projeto
API Rest de exemplo para gerenciar contas pagas e em aberto.
A Api pode ser executada em um conteiner Docker. Ela também possue
um sistema de autenticação que gera um token Jwt para o usuario acesar
a API. E para a gestão do banco de dados é utlizado o Flayway.



## Tecnologias

- Java JDK 22     -> https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html
- Maven           -> https://maven.apache.org/
- SpringBoot      -> https://spring.io/projects/spring-boot
- Spring Data JPA -> https://spring.io/projects/spring-data
- Spring Security -> https://spring.io/projects/spring-security
- Jwt             -> https://jwt.io/
- Lombok          -> https://projectlombok.org/
- Hibernate       -> https://hibernate.org/
- PostgreSql      -> https://hibernate.org/
- Docker          -> https://www.docker.com/
- JUnit           -> https://junit.org/
- Mockito         -> https://site.mockito.org/
- Flyway          -> https://flywaydb.org/

## Executar o Projeto
Pode-se executar o projeto atraves do Docker Composer. Ira ser criado um conteiner
com todos os serviços. A Aplicação roda na porta 8080. 
Se for executar a aplicação fora do conteiner ela esta rondando na porta 8081.
``` 
Comando para executar no docker => docker compose up --build 
```


## API

```http
  POST /api/v1/contas/create
```
<br>

```http
  GET /api/v1/contas/find/{id}
```
<br>

```http
  GET /api/v1/contas/find/all
```
<br>

```http
  PUT /api/v1/contas/update/{id}
```
<br>

```http
  DELETE /api/v1/contas/delete/{id}
```
<br>

```http
  PATCH /api/v1/contas/update/{id}
```
<br>

```http
  POST /api/v1/contas/upload/csv
```
<br>

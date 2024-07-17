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
com todos os serviços. A Aplicação roda em http:\\localhost:8080. 

``` 
Comando para executar no docker => docker compose up --build 
```
## API - Autenticação
```http
  POST /api/v1/auth
```
* Obtem o Token(Bearer) Jwt de acesso <br>

```
 curl --location 'localhost:8080/api/auth' \
--header 'Content-Type: application/json' \
--data '{
    "username":"master",
    "password":"master"
}'

Exemplo:
Request Body:

 {
  "username":"master",
  "password":"master"
 } 
    
Response: 
 {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXN0ZXIiLCJpYXQiOjE3MTg5OTY2NjgsImV4cCI6MTcxODk5ODU2OH0.I3BmbkgE6kyDjf-7JFGJiI0vKEmgo3xOkVYKRI1317cXK6SxcyKCgg7fyDFe-2iFMOYV5keXpUUcZ32nJICoSQ"
 }  
 


             
```

* Autenticaçao da Api é via Header -> Authorization.

```
 Authorization :  Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXN0ZXIiLC...
```

## API - Contas


```http
  POST /api/v1/contas/create
```
* Cadastra uma nova conta <br>
<b>Body</b> - Objeto Json representando uma entidade Conta.
```
    {
        "dataVencimento": "2024-06-01",
        "dataPagamento":"2024-06-02",
         "valor": 159.78,
         "descricao": "Luz",
         "situacao": "PAGA"
    }             
```
* Curl

        curl --location 'localhost:8080/api/v1/contas/create' \
        --header 'Content-Type: application/json' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXN0ZXIiLCJpYXQiOjE3MTg5...' \
        --data '{
        "dataVencimento": "2024-06-01",
        "dataPagamento":"2024-06-02",
        "valor": 159.78,
        "descricao": "Luz",
        "situacao": "PAGA"
        } '

<br>

```http
  GET /api/v1/contas/find/{id}
```
* Busca uma conta especifica pelo ID da mesma.

| Parametro    | Tipo         | Descrição                                                                                 |
|:-------------|:-------------|:------------------------------------------------------------------------------------------|
| `id`         | `int `       | Id de indentificação da conta                                                             |

* Curl
  
        curl --location 'localhost:8080/api/v1/contas/find/2' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...'

<br>
<br>

```http
  GET /api/v1/contas/find
```
* Realizar uma busca pagianda e pelos filtros de Data de Vencimento e Descrição 
em todas a contas já cadastradas

| Parametro        | Tipo      | Descrição                     |
|:-----------------|:----------|:------------------------------|
| `pageNumber`     | `int `    | Numero da Pagina              |
| `pageSize`       | `int `    | Numero de elementos na Pagina |
| `descricao`      | `String ` | Descrição da conta            |
| `dataVencimento` | `String ` | Data de vencimento            |

<br>

* Curl: 

      curl --location 'localhost:8080/api/v1/contas/find?pageNumber=0&pageSize=05&dataVencimento=2024-05-02&descricao=Produto' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXN0ZXIiLCJ...' \
      --data ''

```http
  PUT /api/v1/contas/update/{id}
```

* Realizar uma atualização de toda a conta.

| Parametro    | Tipo         | Descrição                                                                                 |
|:-------------|:-------------|:------------------------------------------------------------------------------------------|
| `id`         | `int `       | Id de indentificação da conta                                                             |

<b>Body</b> - Objeto Json representando uma entidade Conta.
```
    {
        "dataVencimento": "2024-06-01",
        "dataPagamento":"2024-06-02",
         "valor": 159.78,
         "descricao": "Luz",
         "situacao": "PAGA"
    }             
```

* Curl

      curl --location --request PUT 'localhost:8080/api/v1/contas/update/1' \
      --header 'Content-Type: application/json' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...' \
      --data '{
      "dataVencimento": "2024-06-01",
      "dataPagamento":"2024-06-02",
      "valor": 159.78,
      "descricao": "Luz",
      "situacao": "PAGA"
      }    '
<br>

```http
  DELETE /api/v1/contas/delete/{id}
```

* Exclui uma conta através do Id.

| Parametro            | Tipo      | Descrição                                                                                 |
|:---------------------|:----------|:------------------------------------------------------------------------------------------|
| `id`                 | `int `    | Id de indentificação da conta                                                             |

* Curl

        curl --location --request DELETE 'localhost:8080/api/v1/contas/delete/1' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMi...'
<br>
<br>

```http
  PATCH /api/v1/contas/update/{id}
```
* Realiza Update da Situação da conta. PAGA ou PENDENTE

| Parametro            | Tipo      | Descrição                                                                                 |
|:---------------------|:----------|:------------------------------------------------------------------------------------------|
| `id`                 | `int `    | Id de indentificação da conta                                                             |
| `situacao`           | `string ` | Situação da conta PAGA ou PENDENTE                                                        |
                                                        |

* Curl

      curl --location --request PATCH 'localhost:8080/api/v1/contas/update/2?situacao=PENDENTE' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...'

<br>
<br>

```http
  POST /api/v1/contas/upload/csv     
```
* Realizar upload de um arquivo CSV contendo dados de contas, separados por ";".
* Campos do arquivo: dataVencimento,dataPagamento,valor,descricao,situacao

| Parametro | Tipo               | Descrição                                  |
|:----------|:-------------------|:-------------------------------------------|
| `file`    | `string ($binary)` | Realiza o Upload de um arquivo do tipo CSV |
<br>

* Curl

        curl --location 'localhost:8080/api/v1/contas/upload/csv' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...' \
        --form 'file=@"/C:/Users/leona/Downloads/Pedidos(Planilha1).csv"'
<br>
<br>

```http
  GET /api/v1/contas/totalPago
```
* Realizar uma busca pelo valor total ja gastos em contas, com intervalo de datas.

| Parametro        | Tipo    | Descrição                           |
|:-----------------|:--------|:------------------------------------|
| `dataInicio`     | `date ` | Filtro Inicio da data para pesquisa |
| `dataFim`       | `date ` | Filtro Fim da data para pesquisa    |

 * Curl
   
        curl --location 'localhost:8080/api/v1/contas/totalPago?dataInicio=2024-05-01&dataFim=2024-06-24' \
         --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...'

## Collections


## Author


[@leokashmir](https://www.github.com/leokashmir)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/leokashmir/)

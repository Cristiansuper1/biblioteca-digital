# biblioteca-digital

API REST para gerenciamento de uma biblioteca digital com sistema de favoritos por usuário.
Feito em Java 17 + Spring Boot como projeto de portfólio.

---

## Stack

- Java 17
- Spring Boot 4.0.2
- Spring Data JPA
- H2 Database (em memória)
- Maven
- Swagger / OpenAPI 3

---

## Como rodar

Pré-requisito: Java 17 instalado.
```bash
git clone https://github.com/Cristiansuper1/biblioteca-digital.git
cd biblioteca-digital
./mvnw spring-boot:run
```

Sobe em `http://localhost:8080`.

---

## Documentação

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

Todos os endpoints com exemplos de request e response.

<img width="2838" height="989" alt="image" src="https://github.com/user-attachments/assets/c059afae-f189-46ef-bc6a-660e62bd13fb" />

<img width="2842" height="1133" alt="image" src="https://github.com/user-attachments/assets/80c2c47d-dfab-4483-b6aa-ed165253cf5d" />

---

## Endpoints

### Usuários

| Método | Rota | Ação |
|--------|------|------|
| GET | `/usuarios` | Lista todos |
| GET | `/usuarios/{id}` | Busca por ID |
| POST | `/usuarios` | Cria usuário |
| PUT | `/usuarios/{id}` | Atualiza usuário |
| DELETE | `/usuarios/{id}` | Remove usuário |

### Livros

| Método | Rota | Ação |
|--------|------|------|
| GET | `/livros` | Lista todos |
| GET | `/livros/{id}` | Busca por ID |
| POST | `/livros` | Cadastra livro |
| PUT | `/livros/{id}` | Atualiza livro |
| DELETE | `/livros/{id}` | Remove livro |

### Favoritos

| Método | Rota | Ação |
|--------|------|------|
| GET | `/usuarios/{id}/favoritos` | Lista favoritos do usuário |
| POST | `/usuarios/{uid}/favoritos/{lid}` | Adiciona favorito |
| DELETE | `/usuarios/{uid}/favoritos/{lid}` | Remove favorito |

---

## Exemplos

**Criar usuário**
```http
POST /usuarios
Content-Type: application/json

{
  "nome": "Cristian",
  "email": "cristian@email.com"
}
```
```json
{
  "id": 1,
  "nome": "Cristian",
  "email": "cristian@email.com"
}
```

**Adicionar favorito**
```http
POST /usuarios/1/favoritos/3
```

---

## Validações

- `nome` e `email` obrigatórios (`@NotBlank`)
- `email` com formato válido (`@Email`)
- Validação falha: `400 Bad Request` com mensagem descritiva
- Recurso não encontrado: `404 Not Found`

Erros passam pelo `GlobalExceptionHandler`.

---

## Testes
```bash
./mvnw test
```

Cobre repositórios JPA e controllers via MockMvc.

---

## Banco de dados

H2 em memória. Os dados não são persistentes, resetam em toda execução.

Console: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:mem:testdb`

---

## Estrutura
```
src/
├── main/
│   ├── java/com/biblioteca/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── exception/
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/biblioteca/
```

---

## Licença

MIT

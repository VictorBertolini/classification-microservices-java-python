# 📄 Text Classification API

Sistema de classificação de texto baseado em arquitetura de microsserviços, desenvolvido para explorar a comunicação entre serviços em linguagens distintas e colocar em prática conceitos de segurança, autenticação e integração com modelos de linguagem.

---

## 👨‍💻 Autor

**Victor Bertolini de Sousa**
[LinkedIn](https://www.linkedin.com/in/victor-bertolini-de-sousa-6b8630394/) 

[GitHub](https://github.com/VictorBertolini)

---

## 🏗️ Arquitetura

![Diagram](/DiagramTextClassificationAPI.svg)

O projeto é composto por dois serviços independentes:

**CentralAPI (Java / Spring Boot)** - serviço principal responsável por:
- Cadastro e autenticação de usuários via JWT
- Controle de planos e limites de requisições por usuário
- Comunicação segura com a API de classificação
- Persistência dos dados no MySQL via Hibernate

**ClassifyAPI (Python / FastAPI)** — serviço de classificação responsável por:
- Receber textos e classificá-los como positivo ou negativo
- Utilizar um modelo de linguagem multilingual via HuggingFace Transformers
- Validar a autenticidade das requisições via API Key

O fluxo completo é: `Cliente → CentralAPI → ClassifyAPI → Modelo HuggingFace`

---

## ✨ Funcionalidades

- Cadastro de usuários com senha protegida por BCrypt
- Autenticação via JWT com expiração de 2 horas
- Três planos de uso: **Free** (100 req/dia), **Premium** (1000 req/dia) e **Admin** (infinitas)
- Alteração de Plano só pode ser feita por **Admin**
- Reset automático de requisições diariamente à meia-noite
- Classificação de texto individual e em lote (batch)
- Comunicação segura entre serviços via API Key
- Tratamento de erros padronizado em ambas as APIs
- Validação de entrada em ambas as camadas

---

## 🛠️ Tecnologias

**CentralAPI**
- Java 21
- Spring Boot
- Spring Security + JWT (Auth0)
- Spring Data JPA + Hibernate
- Flyway (migrations)
- MySQL 8.0.44
- Lombok

**ClassifyAPI**
- Python 3.12
- FastAPI
- HuggingFace Transformers
- Uvicorn
- python-dotenv

---

## 📋 Pré-requisitos

- Java 21
- Python 3.12
- MySQL 8.0.44

---

## 🚀 Como rodar

### Banco de dados

Crie um banco de dados MySQL:

```sql
CREATE DATABASE text_classification_db;
```

As tabelas são criadas automaticamente pelo Flyway ao subir a CentralAPI.

---

### ClassifyAPI (Python)

1. Acesse a pasta do serviço:
```bash
cd ClassifyAPI
```

2. Instale as dependências:
```bash
pip install -r requirements.txt
```

3. Crie o arquivo `.env` na raiz da pasta `ClassifyAPI`:
```env
INTERNAL_API_KEY=sua-chave-secreta
```

4. Suba o serviço:
```bash
uvicorn src.app.main:app
```

O serviço estará disponível em `http://localhost:8000`.
Documentação automática (Swagger) disponível em `http://localhost:8000/docs`.

---

### CentralAPI (Java)

1. Acesse a pasta do serviço:
```bash
cd CentralAPI
```

2. Crie o arquivo `application-local.properties` em `src/main/resources/`:
```properties
api.database.url=jdbc:mysql://localhost:3306/text_classification_db
api.database.user=seu-usuario
api.database.password=sua-senha
api.model.url=http://127.0.0.1:8000
external.key=sua-chave-secreta
jwt.security=seu-segredo-jwt
```

> ⚠️ A `external.key` deve ser igual à `INTERNAL_API_KEY` configurada na ClassifyAPI.

3. Suba o serviço pela sua IDE (IntelliJ recomendado) ou via Maven:
```bash
./mvnw spring-boot:run
```

O serviço estará disponível em `http://localhost:8080`.

---

## 🔑 Variáveis de ambiente

| Variável | Serviço | Descrição |
|---|---|---|
| `INTERNAL_API_KEY` | ClassifyAPI | Chave de autenticação entre serviços |
| `api.database.url` | CentralAPI | URL de conexão com o MySQL |
| `api.database.user` | CentralAPI | Usuário do banco |
| `api.database.password` | CentralAPI | Senha do banco |
| `api.model.url` | CentralAPI | URL base da ClassifyAPI |
| `external.key` | CentralAPI | Chave enviada para a ClassifyAPI (igual à INTERNAL_API_KEY) |
| `jwt.security` | CentralAPI | Segredo usado para assinar os tokens JWT |

---

## 📡 Endpoints principais

### Autenticação

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/user` | Cadastrar novo usuário | Não |
| `POST` | `/login` | Autenticar e obter token JWT | Não |

### Usuário

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `GET` | `/user` | Listar todos os usuários | Admin |
| `GET` | `/user/{id}` | Buscar usuário por ID | Admin |
| `PUT` | `/user/role` | Atualizar role do usuário | Admin |
| `GET` | `/user/remain-requests` | Consultar requisições restantes | Usuário |
| `PUT` | `/user` | Atualizar dados do próprio usuário | Usuário |
| `DELETE` | `/user` | Deletar própria conta | Usuário |

### Classificação

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/classify` | Classificar um texto | Usuário |
| `POST` | `/classify/batch` | Classificar múltiplos textos | Usuário |

> Todos os endpoints autenticados requerem o header `Authorization: Bearer {token}`.

---

## 🔒 Segurança

- Senhas armazenadas com hash BCrypt
- Autenticação stateless via JWT (expiração de 2 horas)
- Comunicação entre serviços protegida por API Key via header
- Usuários só acessam e modificam os próprios dados
- Endpoints administrativos restritos ao papel ADMIN

---
## 📨 Exemplos de requisições e respostas

### Cadastrar usuário
`POST /user`
```json
// Request
{
  "username": "Victor",
  "email": "victor@email.com",
  "password": "minhasenha123"
}

// Response 201
{
  "id": 1,
  "username": "Victor",
  "email": "victor@email.com"
}
```

### Login
`POST /login`
```json
// Request
{
  "email": "victor@email.com",
  "password": "minhasenha123"
}

// Response 200
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Classificar texto
`POST /classify`
```json
// Request
{
  "text": "Esse produto é incrível, adorei!"
}

// Response 200
{
  "text": "Esse produto é incrível, adorei!",
  "label": "positive",
  "score": 0.9821
}
```

### Classificar em lote
`POST /classify/batch`
```json
// Request
[
  { "text": "Adorei o atendimento!" },
  { "text": "Péssima experiência, nunca mais." }
]

// Response 200
[
  { "text": "Adorei o atendimento!", "label": "positive", "score": 0.9743 },
  { "text": "Péssima experiência, nunca mais.", "label": "negative", "score": 0.9612 }
]
```

---

## 🔐 Autenticação JWT

O fluxo de autenticação funciona da seguinte forma:

1. O cliente cadastra um usuário em `POST /user`
2. O cliente autentica em `POST /login` e recebe um token JWT
3. O token deve ser enviado em todas as requisições protegidas no header:
```
Authorization: Bearer {token}
```
4. O token é validado a cada requisição pelo `SecurityFilter` antes de chegar ao endpoint
5. O token expira após **2 horas** — após isso, um novo login é necessário

---

## 🔑 Comunicação entre serviços

A CentralAPI se comunica com a ClassifyAPI através de uma API Key compartilhada. Essa chave é enviada automaticamente em todas as requisições no header:

```
api_key: {chave-secreta}
```

A ClassifyAPI rejeita qualquer requisição que não contenha a chave correta, garantindo que apenas a CentralAPI consiga consumir o serviço de classificação diretamente.

As chaves devem ser iguais nas duas configurações:
- CentralAPI: `external.key` no `application-local.properties`
- ClassifyAPI: `INTERNAL_API_KEY` no `.env`

---

## 📊 Planos e limites de requisições

| Plano | Requisições por dia | Observação |
|---|---|---|
| Free | 100 | Plano padrão ao cadastrar |
| Premium | 1.000 | Atribuído por um administrador |
| Admin | infinitas | Acesso total ao sistema |

O limite de caracteres por texto é de **1.000 caracteres**, validado tanto na CentralAPI quanto na ClassifyAPI.

---

## 🔄 Reset de requisições

As requisições de todos os usuários são resetadas automaticamente **todo dia à meia-noite** para o limite do plano de cada um.

A implementação utiliza `@Scheduled` do Spring com uma expressão cron (`0 0 0 * * *`) e uma query JPQL em batch diretamente no banco:

```sql
UPDATE user_tb SET requests_remain =
  CASE role
    WHEN 'FREE'    THEN 100
    WHEN 'PREMIUM' THEN 1000
    WHEN 'ADMIN'   THEN 1
  END
```

Essa abordagem atualiza todos os registros em uma única operação no banco, sem carregar os objetos na memória.

OBS: Usuários ADMIN não possuem limite de requisições e não participam da lógica de decremento/reset.

---

## ⚠️ Códigos de resposta HTTP

| Status | Significado | Quando ocorre |
|---|---|---|
| `200` | OK | Requisição bem-sucedida |
| `201` | Created | Usuário criado com sucesso |
| `400` | Bad Request | JSON inválido ou campo em branco |
| `401` | Unauthorized | Credenciais inválidas ou token ausente |
| `402` | Too Many Requests | Limite de requisições do plano atingido |
| `403` | Forbidden | Usuário sem permissão para o recurso |
| `404` | Not Found | Usuário não encontrado |
| `406` | Not Acceptable | Texto ultrapassa o limite de 1.000 caracteres |
| `502` | Bad Gateway | ClassifyAPI retornou erro interno |
| `503` | Service Unavailable | ClassifyAPI inacessível ou fora do ar |


---

## 💡 Decisões técnicas

**Por que microsserviços?**
O objetivo principal foi aprender como dois serviços em linguagens diferentes se comunicam. Java pela familiaridade e robustez no backend, Python pela facilidade de integração com modelos de linguagem.

**Por que Python para a classificação?**
Modelos de linguagem têm integração muito mais simples no ecossistema Python (HuggingFace, PyTorch). A ClassifyAPI serve como base: basta trocar o modelo e os DTOs para usar qualquer outra IA treinada com PyTorch.

**Por que este modelo?**
O modelo `lxyuan/distilbert-base-multilingual-cased-sentiments-student` foi escolhido por suportar múltiplos idiomas incluindo português e ser leve o suficiente para rodar localmente sem GPU.

---

## 🗂️ Estrutura do repositório

```
text-classification-api/
├── CentralAPI/         # Serviço principal em Java
└── ClassifyAPI/        # Serviço de classificação em Python
```

## 🗂️ Estrutura das APIs

### CentralAPI (Java)

```
CentralAPI/
└── src/main/java/com/bertolini/CentralAPI/
    ├── controller/         # Endpoints REST (autenticação, usuário, classificação)
    ├── domain/             # Entidades JPA (User, TextClassify, UserRole)
    ├── error/              # Tratamento global de erros (@RestControllerAdvice)
    ├── repository/         # Interfaces JPA
    ├── schema/             # DTOs e exceções customizadas
    │   ├── error/          # Exceções e ErrorResponse
    │   ├── security/       # JWTTokenDTO
    │   ├── text/           # DTOs de classificação
    │   └── user/           # DTOs de usuário
    ├── security/           # Filtro JWT e configuração do Spring Security
    └── service/
        ├── classification/ # Lógica de classificação e client HTTP
        ├── requestServices/# Validação e reset de requisições
        ├── security/       # TokenService e AuthenticationService
        └── user/           # UserService
```

### ClassifyAPI (Python)

```
ClassifyAPI/
└── src/
    ├── app/                # Inicialização da aplicação FastAPI
    ├── controller/         # Endpoints REST (classificação individual e batch)
    ├── dependencies/       # Injeção de dependências e configuração do .env
    ├── domain/             # DTOs de entrada (TextDTO)
    ├── error/              # Exceções customizadas e handlers de erro
    └── service/            # Lógica de classificação e validação de requisições
```

---
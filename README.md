# Projeto Backend - Sistema de Pedidos

## Sobre o projeto

Este projeto consiste em uma API REST desenvolvida com Java e Spring Boot para gerenciamento de pedidos de uma loja/restaurante, permitindo controle de usuários, produtos, pedidos e pagamentos.

A aplicação foi construída seguindo boas práticas de arquitetura backend, utilizando autenticação e autorização com Spring Security, persistência com PostgreSQL e documentação com Swagger/OpenAPI.

O sistema possui um fluxo completo de pedidos, desde a criação até a entrega, incluindo controle de pagamento e regras de negócio.

---

# Funcionalidades

## Usuários
- Cadastro de usuários
- Controle de permissões por perfil:
  - ADMIN
  - GERENTE
  - ATENDENTE
  - CLIENTE
- Autenticação com Spring Security

## Produtos
- Cadastro de produtos
- Consulta de produtos
- Consulta de produto por ID
- Controle de estoque

## Pedidos
- Criação de pedidos
- Consulta de pedido
- Alteração de status do pedido
- Cancelamento de pedido
- Controle de fluxo operacional

## Pagamentos
- Registro de pagamentos
- Aprovação de pagamento
- Geração de código de transação
- Associação entre pedido e pagamento

---

# Tecnologias utilizadas

## Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Hibernate
- Maven

## Banco de dados
- PostgreSQL

## Documentação
- Swagger / OpenAPI

## Ferramentas
- Postman
- IntelliJ IDEA
- Git e GitHub

---

# Estrutura do projeto

```bash
src/main/java
├── controller
├── service
├── repository
├── model
├── dto
├── config
└── security
```

A aplicação segue arquitetura em camadas:

- Controller → Recebe requisições HTTP
- Service → Contém regras de negócio
- Repository → Comunicação com banco de dados
- Model → Entidades JPA
- DTO → Objetos de transferência de dados

---

# Regras de negócio

## Fluxo do pedido

```text
AGUARDANDO_PAGAMENTO
        ↓
PAGO
        ↓
EM_PREPARO
        ↓
AGUARDANDO_ENTREGADOR
        ↓
SAIU_PARA_ENTREGA
        ↓
ENTREGUE
```

Também é possível cancelar pedidos antes da entrega.

---

# Segurança

A aplicação utiliza Spring Security com controle de acesso por roles.

Exemplo:

```java
@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
```

Perfis disponíveis:

- ADMIN
- GERENTE
- ATENDENTE
- CLIENTE

---

# Entidades principais

## Produto

Representa os produtos disponíveis para venda.

Campos:
- id
- nome
- preco
- estoque

---

## Pedido

Representa um pedido realizado por um cliente.

Campos:
- id
- cliente
- canalPedido
- statusPedido
- valorPedido

---

## ItemPedido

Representa os itens vinculados ao pedido.

Campos:
- produto
- quantidade
- preco

---

## Pagamento

Representa o pagamento de um pedido.

Campos:
- pedido
- formaPagamento
- statusPagamento
- valor
- codigoTransacao

---

# Formas de pagamento

```text
PIX
CARTAO_CREDITO
CARTAO_DEBITO
DINHEIRO
```

---

# Status de pagamento

```text
PENDENTE
APROVADO
RECUSADO
```

---

# Como executar o projeto

## Pré-requisitos

- Java 21
- Maven
- PostgreSQL

---

## Clonar repositório

```bash
git clone https://github.com/Moyses-Zerbieti/projeto-Uninter.git
```

---

## Configurar banco PostgreSQL

Criar banco:

```sql
CREATE DATABASE projeto_uninter;
```

---

## Configurar variáveis no application.yml

```yml
spring:
  application:
    name: projeto-backend-uninter

  datasource:
    url: ${DATASOURCE_URL}
    username: ${DATASOURCE_USERNAME}
    password: ${DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update

    show-sql: true
```

---

## Configurar arquivo .env

O projeto utiliza variáveis de ambiente para configuração do banco de dados.

Crie um arquivo chamado `.env` na raiz do projeto utilizando o `.env.template` como base.

Exemplo do `.env.template`:

```env
DATASOURCE_URL=jdbc:postgresql://localhost:5432/NOME-DO-BANCO
DATASOURCE_USERNAME=NOME-DE-USUARIO
DATASOURCE_PASSWORD=SUA-SENHA
```

Após configurar o `.env`, basta iniciar a aplicação normalmente.

---

## Executar aplicação

### Linux/Mac

```bash
./mvnw spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

---

# Swagger

Após iniciar a aplicação:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Exemplos de endpoints

## Criar produto

```http
POST /produto/criar
```

Body:

```json
{
  "nome": "Hamburguer",
  "preco": 35.0,
  "estoque": 10
}
```

---

## Criar pedido

```http
POST /pedido/pedir
```

Body:

```json
{
  "clienteId": "UUID",
  "canalPedido": "APP",
  "itens": [
    {
      "produtoId": "UUID",
      "quantidade": 1
    }
  ]
}
```

---

## Efetuar pagamento

```http
POST /pagamento/{pedidoId}/pagar
```

Body:

```json
{
  "formaPagamento": "PIX"
}
```

---

# Collection Postman

A aplicação possui uma collection completa do Postman contendo os endpoints utilizados durante o desenvolvimento e testes da API.

A collection inclui:

- Usuários
- Produtos
- Pedidos
- Pagamentos
- Fluxo completo de status
- Testes de respostas HTTP
- Regras de negócio

## Arquivo da collection

A collection exportada está disponível na pasta:

```/postman```

---

# Tratamento de erros

A API retorna respostas HTTP apropriadas para cada cenário:

| Código | Descrição |
|---|---|
| 200 | Sucesso |
| 201 | Criado com sucesso |
| 401 | Não autorizado |
| 403 | Acesso negado |
| 404 | Recurso não encontrado |
| 422 | Regra de negócio violada |
| 500 | Erro interno |

---

# Autor

## Moyses Zerbieti / RU: 4670083

Projeto desenvolvido para fins acadêmicos e prática de desenvolvimento backend com Java e Spring Boot.

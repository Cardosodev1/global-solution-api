# 🚀 Global Solution API - Career & Skill Analysis

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-green)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Messaging-orange)
![Redis](https://img.shields.io/badge/Redis-Caching-red)

## 📖 Sobre o Projeto

Este projeto foi desenvolvido como parte da **Global Solution (FIAP)**. Trata-se de uma API RESTful inteligente que atua como um assistente de carreira, conectando candidatos a vagas de emprego através de análise de dados e Inteligência Artificial.

O sistema permite que o usuário cadastre seu currículo e analise vagas de emprego. A API utiliza IA para extrair competências (skills), identifica lacunas (GAP Analysis) entre o perfil do candidato e a vaga, e recomenda automaticamente recursos de aprendizado para as skills faltantes.

## 🏗️ Arquitetura e Diferenciais Técnicos

O projeto vai além de um CRUD tradicional, implementando uma arquitetura robusta orientada a performance e escalabilidade:

* **Processamento Assíncrono (Event-Driven):** Utilização de **RabbitMQ** para processar as análises de vagas em segundo plano, garantindo que a API não trave durante chamadas lentas de IA.
* **Estratégia de Caching:** Implementação de **Redis** para armazenar o catálogo de skills e resultados de análises, reduzindo drasticamente as chamadas ao banco de dados e à API externa.
* **Inteligência Artificial:** Integração com API externa de LLM para extração semântica de skills a partir de textos em linguagem natural.
* **Resiliência:** Tratamento de concorrência (Race Conditions) e serialização segura.
* **Dockerizado:** Ambiente de desenvolvimento completo orquestrado via Docker Compose.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3 (Web, Data JPA, Security, Validation, AMQP, Cache)
* **Banco de Dados:** Oracle Database (Cloud)
* **Mensageria:** RabbitMQ
* **Cache:** Redis
* **Segurança:** Spring Security + JWT (JSON Web Token)
* **Build:** Maven
* **Containerização:** Docker & Docker Compose

## ⚙️ Pré-requisitos

* **Docker** e **Docker Compose** instalados.
* (Opcional) Java 21 e Maven se for rodar fora do Docker.

## 🚀 Como Rodar o Projeto

### 1. Configuração de Ambiente

Crie um arquivo `.env` na raiz do projeto (baseado no `.env.example`) e preencha as credenciais:

```properties
# Banco de Dados (Oracle Externo)
DB_ORACLE=seu_usuario
DB_PASSWORD=sua_senha

# Configurações da API
API_KEY=sua_chave_da_api_ia
JWT_SECRET=sua_chave_secreta_para_token
```

### 2. Execução com Docker (Recomendado)

Execute o comando abaixo na raiz do projeto para compilar a aplicação, subir o banco de dados (cache/fila) e a API:

```properties
docker compose up --build -d
```

A API estará disponível em: http://localhost:8080/api/v1

* RabbitMQ Management: http://localhost:15672 (Login: guest/guest)

## 🔌 Endpoints

### **Autenticação**
* `POST /auth/register` - Criar nova conta.
* `POST /auth/login` - Obter Token JWT.

### **Usuário**
* `GET /user/me` - Obter dados do perfil logado.
* `PUT /user/me` - Atualizar dados do perfil logado.
* `DELETE /user/me` - Deletar o perfil logado.

### **Currículo (Resume)**
* `POST /resume` - Cadastrar currículo (IA extrai skills automaticamente).
* `GET /resume` - Obter todos os currículos do perfil logado.
* `GET /resume/{id}` - Detalhes do currículo (Cacheado no Redis).
* `PUT /resume/{id}` - Atualizar dados do currículo do perfil logado.
* `DELETE /resume/{id}` - Deletar currículo do perfil logado (Cacheado no Redis).

### **Análise de Vaga (Analysis)**
* `POST /analysis` - Envia uma vaga para análise.
  * Retorno: `200 OK` com status `PENDING` (Processamento assíncrono iniciado).
* `GET /analysis` - Obtém dados simplificados de todas as análises do perfil logado.
  * Retorno: Título da vaga e data de criação.
* `GET /analysis/{id}` - Obtém o resultado da análise.
  * Retorno: Lista de Skills (MATCH, GAP, EXTRA) e links de estudo.
* `DELETE /analysis/{id}` - Delete a análise.
  * Retorno: `204 No Content`.

## 🧪 Fluxo de Teste Sugerido

1. Crie um usuário e faça login para obter o Bearer Token.

2. Cadastre um currículo com suas skills.

3. Envie uma descrição de vaga para o endpoint de análise.

4. Aguarde alguns segundos (processamento da fila) e consulte o ID da análise.

5. Verifique se o status mudou de PENDING para COMPLETED e veja as recomendações de estudo.

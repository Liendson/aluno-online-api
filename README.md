# Sistema de Aluno Online

Este projeto é um sistema feito na Disciplina de Backend da Uniesp.

## Índice
- [Arquitetura](#arquitetura)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
---

## Arquitetura
- O Sistema usa a Arquitetura MVC.
- Na pasta Controller estão configurados os endpoints da api.
- Na pasta Service, a camada de negócio, que é chamada pelo Controller.
- Na pasta Repository, a camada de dados que por sua vez, se comunica com o Banco de Dados.
- Além disso, as demais pastas servem para organização do projeto, como DTO's, Enums e etc.
---

## Tecnologias Utilizadas
- **Backend:** Java com Spring.
- **Banco de Dados:** PostgresSQL.
- **Docker:** Utilizado para criar a imagem com a instância do banco
---

O Projeto basicamente é um conjunto de CRUD's (Aluno, Professor, Disciplina e Matrícula).
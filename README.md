# Sistema de Empréstimo de Equipamentos

Projeto acadêmico da UNISA — AVC 2026/2, desenvolvido para a disciplina de Técnicas de Programação.

O sistema tem como objetivo gerenciar empréstimos e devoluções de equipamentos, relacionando usuários, equipamentos e seus respectivos registros.

## Tecnologias

- Java
- Spring Boot
- JDBC
- PostgreSQL
- Supabase
- Maven

## Documentação

A documentação técnica, incluindo a arquitetura do sistema, o diagrama UML e os endpoints de empréstimos, está disponível no README do projeto Java:

[**Acessar documentação técnica**](avc-equipamentos/README.md)

## Executar o projeto

Entre na pasta da aplicação:

```bash
cd avc-equipamentos
```

Execute os testes com Maven:

```bash
mvn test
```

Configure as variáveis de ambiente `DB_URL`, `DB_USER` e `DB_PASSWORD` antes de executar a aplicação com acesso ao banco de dados.

## Organização

O código-fonte da aplicação está localizado na pasta `avc-equipamentos`.

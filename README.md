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

---

## Módulo de Utilizadores e Tratamento Global de Erros (Beatriz Pereira)

### 1. Visão Geral da Implementação (Tarefas T-11 a T-14)
- **Modelos e DTOs (T-11):** Implementação do `UsuarioResponseDTO` para transferir apenas os dados necessários dos utilizadores (`id`, `nome`, `matricula`), preservando o encapsulamento das entidades de domínio.
- **Acesso a Dados (T-12):** Utilização do `UsuarioDAO` para consultas diretas via JDBC e `PreparedStatement`, garantindo o encerramento correto dos recursos e a prevenção de injeção de SQL.
- **Serviço e Endpoints REST (T-13):** Criação do `UsuarioController` expondo os seguintes contratos:
  - `GET /usuarios`: Lista todos os utilizadores cadastrados com status HTTP 200.
  - `GET /usuarios/{id}`: Consulta detalhada de um utilizador por identificador único, retornando HTTP 200 ou acionando erro HTTP 404 caso não exista.
- **Tratamento Global de Erros (T-14):** Implementação de arquitetura centralizada com `@RestControllerAdvice` através da classe `GlobalExceptionHandler`, padronizando todas as respostas de falha no formato `ApiErrorDTO` (timestamp, código HTTP, tipo do erro e mensagem descritiva):
  - **HTTP 404 (Not Found):** Mapeado via `RecursoNaoEncontradoException`.
  - **HTTP 400 (Bad Request):** Mapeado via `RegraNegocioException`.
  - **HTTP 500 (Internal Server Error):** Mapeado para falhas de persistência (`SQLException`) e exceções gerais não previstas (`Exception`).

---

## Declaração sobre o Uso de Inteligência Artificial (Tarefa T-15)

Em conformidade com os requisitos éticos e académicos da disciplina, o desenvolvimento das tarefas T-11 a T-14 contou com o auxílio de assistente de Inteligência Artificial generativa (Gemini).

### Finalidade do Uso
- **Apoio Estrutural e Arquitetura:** Suporte na padronização das classes de exceção e anotações do Spring Boot (`@RestControllerAdvice`, `@ExceptionHandler`).
- **Resolução de Problemas de Ambiente e Configuração:** Diagnóstico de inconsistências de pacotes no arquivo de testes (`ConnectionFactoryTest`), adequação de comandos entre ambientes Windows (PowerShell) e Codespaces, e orientação sobre a esteira de commits do Git.
- **Validação de Código e Boas Práticas:** Revisão da separação de responsabilidades entre DAO (JDBC nativo), Service, Controller e DTOs, assegurando a não exposição desnecessária dos dados da entidade.

### Supervisão Humana
Todas as soluções sugeridas pela ferramenta foram inspecionadas criticamente, validadas face às regras de negócio do enunciado e testadas no ambiente do projeto antes de serem integradas à branch de trabalho.
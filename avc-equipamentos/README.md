# Sistema de Empréstimo de Equipamentos

Projeto acadêmico desenvolvido para a disciplina de Engenharia de Software da UNISA. O sistema tem como objetivo gerenciar empréstimos e devoluções de equipamentos, relacionando usuários, equipamentos e registros de empréstimo.

## 1. Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Spring Boot** — estrutura da aplicação e exposição dos endpoints REST.
- **JDBC** — comunicação com o banco de dados por meio de comandos SQL.
- **PostgreSQL** — banco de dados relacional.
- **Supabase** — serviço utilizado para disponibilização do PostgreSQL.
- **Maven** — gerenciamento de dependências e execução dos testes.

## 2. Arquitetura do sistema

A aplicação utiliza uma arquitetura em camadas, separando as responsabilidades do código.

| Camada | Responsabilidade |
|---|---|
| `controller` | Receber requisições HTTP e retornar respostas da API. |
| `service` | Aplicar validações e regras de negócio. |
| `repository` | Executar consultas e operações SQL no banco de dados. |
| `model` | Representar as entidades do domínio. |
| `dto` | Transportar os dados de entrada e saída da API. |
| `config` | Centralizar configurações de infraestrutura, incluindo a conexão com o banco. |

### Fluxo de uma requisição

Uma requisição HTTP chega ao controller, que utiliza o service para executar a operação. O service acessa o repository, responsável pela persistência no banco de dados. O resultado retorna pelas camadas até formar a resposta HTTP.

```mermaid
flowchart TD
    A[Cliente HTTP] --> B[Controller]
    B --> C[Service]
    C --> D[Repository / DAO]
    D --> E[ConnectionFactory]
    E --> F[(PostgreSQL / Supabase)]
    F --> E
    E --> D
    D --> C
    C --> B
    B --> G[Resposta HTTP]
```

## 3. Diagrama de classes UML

O diagrama abaixo representa as principais classes identificadas no código da aplicação.

```mermaid
classDiagram
    class AvcEquipamentosApplication {
        +main(String[] args)
    }

    class ConnectionFactory {
        -String url
        -String usuario
        -String senha
        +getConnection() Connection
    }

    class EmprestimoController {
        -EmprestimoService emprestimoService
        +registrarEmprestimo() ResponseEntity
        +registrarDevolucao(Long id) ResponseEntity
    }

    class EmprestimoService {
        -EmprestimoDAO emprestimoDAO
        +registrarEmprestimo(Long usuarioId, Long equipamentoId) Emprestimo
        +registrarDevolucao(Long emprestimoId) void
    }

    class EmprestimoDAO {
        +registrarEmprestimo(Long usuarioId, Long equipamentoId) Emprestimo
        +registrarDevolucao(Long emprestimoId) void
    }

    class EmprestimoRequestDTO {
        -Long usuarioId
        -Long equipamentoId
    }

    class EmprestimoResponseDTO {
        -Long id
        -Long usuarioId
        -Long equipamentoId
        -LocalDateTime dataRegistro
        -LocalDateTime dataDevolucao
        -String situacao
    }

    class Emprestimo {
        -Long id
        -Usuario usuario
        -Equipamento equipamento
        -LocalDateTime dataRegistro
        -LocalDateTime dataDevolucao
        -String situacao
    }

    class Usuario {
        -Long id
        -String nome
        -String matricula
    }

    class Equipamento {
        -Long id
        -String nome
        -String patrimonio
        -boolean disponivel
    }

    class UsuarioDAO
    class UsuarioService

    AvcEquipamentosApplication ..> EmprestimoController : inicia aplicação
    EmprestimoController --> EmprestimoService : utiliza
    EmprestimoController ..> EmprestimoRequestDTO : recebe
    EmprestimoController ..> EmprestimoResponseDTO : retorna
    EmprestimoService --> EmprestimoDAO : utiliza
    EmprestimoDAO --> ConnectionFactory : obtém conexão
    EmprestimoService ..> Emprestimo : manipula
    EmprestimoResponseDTO ..> Emprestimo : converte
    Emprestimo --> Usuario : referencia
    Emprestimo --> Equipamento : referencia
    UsuarioService --> UsuarioDAO : utiliza
```

## 4. Persistência e transações

A classe `ConnectionFactory` centraliza a criação de conexões JDBC. A classe `EmprestimoDAO` executa as operações de empréstimo e devolução utilizando comandos SQL.

As operações de empréstimo e devolução utilizam transações manuais, com `commit` para confirmar as alterações e `rollback` em caso de falha. O empréstimo também utiliza bloqueio de registro durante a verificação da disponibilidade do equipamento, reduzindo o risco de empréstimos simultâneos do mesmo item.

## 5. Endpoints de empréstimos

| Método | Endpoint | Finalidade |
|---|---|---|
| `POST` | `/emprestimos` | Registrar um empréstimo. |
| `PATCH` | `/emprestimos/{id}/devolucao` | Registrar a devolução de um empréstimo. |

### Exemplo de requisição para empréstimo

```json
{
  "usuarioId": 1,
  "equipamentoId": 1
}
```

O endpoint de criação retorna o status HTTP `201 Created` quando a operação é concluída. O endpoint de devolução retorna `204 No Content` em caso de sucesso.

## 6. Configuração do banco de dados

A aplicação utiliza variáveis de ambiente para configurar a conexão com o PostgreSQL:

- `DB_URL` — URL JDBC do banco.
- `DB_USER` — usuário do banco.
- `DB_PASSWORD` — senha do banco.

Essas variáveis devem ser configuradas no ambiente de execução. Credenciais reais não devem ser adicionadas ao repositório.

## 7. Testes

Os testes automatizados estão localizados em `src/test/java`. Para executar a suíte de testes, utilize o Maven na pasta que contém o arquivo `pom.xml`:

```bash
mvn test
```

O resultado da execução deve ser verificado no terminal. A compilação bem-sucedida, isoladamente, não comprova que todos os fluxos da API funcionam corretamente.

## 8. Organização do projeto

```text
src/
├── main/
│   ├── java/br/com/unisa/avc_equipamentos/
│   │   ├── algoritmos/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── AvcEquipamentosApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/br/com/unisa/avc_equipamentos/
```

**Observação:** a documentação deverá ser atualizada conforme as demais funcionalidades do projeto forem integradas pela equipe.

## 9. Módulo de Algoritmos

O módulo de algoritmos foi desenvolvido por Fernando Oliveira e está localizado em `src/main/java/br/com/unisa/avc_equipamentos/algoritmos/ModuloAlgoritmos.java`.

Ele realiza operações sobre uma coleção de equipamentos armazenada em memória, sem utilizar consultas ao banco de dados ou endpoints REST.

### Busca linear — T-17

O método `buscaLinear(String nome)` percorre a lista de equipamentos, comparando seus nomes até encontrar o equipamento desejado.

- Complexidade de tempo: O(n) no pior caso.
- Melhor caso: O(1), quando o equipamento é o primeiro da lista.
- Complexidade de espaço adicional: O(1).

### Busca binária — T-18

O método `buscaBinaria(String nome)` utiliza a busca binária para localizar um equipamento pelo nome. Antes da busca, a lista é ordenada utilizando o InsertionSort.

- Complexidade da busca: O(log n).
- Complexidade da ordenação prévia: O(n²) no pior caso.
- Complexidade total da chamada: O(n²) no pior caso, considerando a ordenação.
- Complexidade de espaço adicional: O(1).

### InsertionSort — T-19

O método `insertionSort()` ordena manualmente os equipamentos por nome, utilizando comparações entre os elementos da lista.

- Melhor caso: O(n), quando a lista já está ordenada.
- Pior caso: O(n²), quando são necessários muitos deslocamentos.
- Complexidade de espaço adicional: O(1).

### Observações

Os algoritmos utilizam a classe `Equipamento` já existente no projeto e comparam os nomes sem diferenciar letras maiúsculas e minúsculas. A implementação pressupõe nomes não nulos para a ordenação e a busca binária.

A compilação do módulo foi concluída com sucesso usando `mvn compile`. Os testes específicos dos algoritmos ainda precisam ser validados. A execução de `mvn test` apresentou falha no teste de conexão com o PostgreSQL devido à ausência de senha no ambiente de execução.
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

## Equipe e contribuições

| Integrante | Contribuições |
|---|---|
| Pedro Reis | Configuração da conexão JDBC, implementação do módulo de empréstimos e documentação técnica. |
| André Roza | Implementação do módulo de equipamentos. |
| Beatriz Pereira | Implementação do módulo de usuários e tratamento global de exceções. |
| Fernando Oliveira | Implementação dos algoritmos de busca e ordenação. |
| Gustavo Dornellas | Implementação dos testes automatizados e validação dos módulos. |

## Documentação

A documentação técnica, incluindo a arquitetura do sistema, o diagrama UML e os endpoints de empréstimos, está disponível no README do projeto Java:

[**Acessar documentação técnica**](avc-equipamentos/README.md)

## Executar o projeto

É necessário ter Java 21 e Maven instalados.

Entre na pasta da aplicação:

```bash
cd avc-equipamentos
```

Configure as seguintes variáveis de ambiente antes de executar a aplicação:

- `DB_URL`: endereço de conexão com o PostgreSQL.
- `DB_USER`: usuário do banco de dados.
- `DB_PASSWORD`: senha do banco de dados.

Execute os testes automatizados:

```bash
mvn clean test
```

Inicie a aplicação:

```bash
mvn spring-boot:run
```

As credenciais do banco de dados devem ser configuradas no ambiente de execução, não diretamente no código-fonte.

## Testes e validação

O projeto possui testes automatizados para os módulos de conexão, aplicação, usuários, empréstimos, algoritmos e tratamento global de exceções.

Na validação integrada, foram executados 39 testes, todos aprovados, sem falhas, erros ou testes ignorados (`BUILD SUCCESS`).

## Declaração sobre o Uso de Inteligência Artificial

Durante o desenvolvimento do projeto, os integrantes utilizaram ferramentas de Inteligência Artificial generativa, especificamente **ChatGPT (OpenAI)** e **Gemini (Google)**, como recursos de apoio às atividades acadêmicas e técnicas.

### Finalidades do uso

As ferramentas foram utilizadas como apoio em atividades como:

- **Desenvolvimento:** esclarecimento de dúvidas sobre Java, Spring Boot, JDBC, SQL e demais tecnologias utilizadas.
- **Arquitetura e organização:** apoio à compreensão da arquitetura em camadas e à separação de responsabilidades.
- **Resolução de problemas:** auxílio no diagnóstico de erros de compilação, configuração, integração e execução, incluindo inconsistências de pacotes em testes e diferenças entre ambientes Windows e Codespaces.
- **Revisão de código:** sugestões de melhorias, identificação de possíveis problemas e discussão de boas práticas, incluindo a separação de responsabilidades entre DAO, Service, Controller e DTOs.
- **Tratamento de exceções:** apoio à compreensão e padronização do uso de `@RestControllerAdvice` e `@ExceptionHandler`.
- **Testes:** orientação sobre elaboração, execução e interpretação de testes automatizados.
- **Documentação e controle de versão:** apoio à elaboração da documentação técnica e à compreensão de procedimentos de Git e commits.

### Supervisão e responsabilidade dos integrantes

As ferramentas de IA foram utilizadas como instrumentos auxiliares. Todas as sugestões foram analisadas criticamente, confrontadas com os requisitos do trabalho e validadas no ambiente do projeto antes de sua integração.

As decisões de implementação, a análise das sugestões, a validação do código e a integração das alterações permaneceram sob responsabilidade dos integrantes.

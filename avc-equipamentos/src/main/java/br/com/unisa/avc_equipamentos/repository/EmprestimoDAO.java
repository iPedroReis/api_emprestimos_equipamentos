
package br.com.unisa.avc_equipamentos.repository;

import br.com.unisa.avc_equipamentos.config.ConnectionFactory;
import br.com.unisa.avc_equipamentos.model.Emprestimo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class EmprestimoDAO {

    private final ConnectionFactory fabricaConexao;

    public EmprestimoDAO(ConnectionFactory fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    public Emprestimo registrarEmprestimo(
            Long usuarioId, Long equipamentoId) throws SQLException {

        String verificarUsuario = """
                SELECT id
                FROM usuarios
                WHERE id = ?
                """;

        String bloquearEquipamento = """
                SELECT id, disponivel
                FROM equipamentos
                WHERE id = ?
                FOR UPDATE
                """;

        String inserirEmprestimo = """
                INSERT INTO emprestimos
                    (usuario_id, equipamento_id, data_registro,
                     data_devolucao, situacao)
                VALUES (?, ?, CURRENT_TIMESTAMP, NULL, 'ATIVO')
                RETURNING id, data_registro, situacao
                """;

        String atualizarEquipamento = """
                UPDATE equipamentos
                SET disponivel = FALSE
                WHERE id = ?
                """;

        try (Connection conexao = fabricaConexao.getConnection()) {
            conexao.setAutoCommit(false);

            try {
                // Confirma que o usuário existe.
                try (PreparedStatement comando =
                             conexao.prepareStatement(verificarUsuario)) {
                    comando.setLong(1, usuarioId);

                    try (ResultSet resultado = comando.executeQuery()) {
                        if (!resultado.next()) {
                            throw new SQLException(
                                    "Usuário não encontrado: " + usuarioId);
                        }
                    }
                }

                // Bloqueia a linha do equipamento durante a transação.
                boolean disponivel;

                try (PreparedStatement comando =
                             conexao.prepareStatement(bloquearEquipamento)) {
                    comando.setLong(1, equipamentoId);

                    try (ResultSet resultado = comando.executeQuery()) {
                        if (!resultado.next()) {
                            throw new SQLException(
                                    "Equipamento não encontrado: "
                                            + equipamentoId);
                        }

                        disponivel = resultado.getBoolean("disponivel");
                    }
                }

                if (!disponivel) {
                    throw new SQLException(
                            "Equipamento indisponível para empréstimo.");
                }

                // Registra o empréstimo.
                Emprestimo emprestimo = new Emprestimo();

                br.com.unisa.avc_equipamentos.model.Usuario usuario =
                new br.com.unisa.avc_equipamentos.model.Usuario();
                usuario.setId(usuarioId);

                br.com.unisa.avc_equipamentos.model.Equipamento equipamento =
                        new br.com.unisa.avc_equipamentos.model.Equipamento();
                equipamento.setId(equipamentoId);

                emprestimo.setUsuario(usuario);
                emprestimo.setEquipamento(equipamento);

                try (PreparedStatement comando = conexao.prepareStatement(
                        inserirEmprestimo,
                        Statement.RETURN_GENERATED_KEYS)) {

                    comando.setLong(1, usuarioId);
                    comando.setLong(2, equipamentoId);

                    try (ResultSet resultado = comando.executeQuery()) {
                        if (!resultado.next()) {
                            throw new SQLException(
                                    "Não foi possível registrar o empréstimo.");
                        }

                        emprestimo.setId(resultado.getLong("id"));
                        emprestimo.setDataRegistro(
                                resultado.getTimestamp("data_registro")
                                        .toLocalDateTime());
                        emprestimo.setSituacao(
                                resultado.getString("situacao"));
                    }
                }

                // Marca o equipamento como indisponível.
                try (PreparedStatement comando =
                             conexao.prepareStatement(atualizarEquipamento)) {
                    comando.setLong(1, equipamentoId);

                    if (comando.executeUpdate() != 1) {
                        throw new SQLException(
                                "Não foi possível atualizar o equipamento.");
                    }
                }

                conexao.commit();

                emprestimo.setDataDevolucao(null);
                return emprestimo;

            } catch (SQLException | RuntimeException erro) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    erro.addSuppressed(erroRollback);
                }

                throw erro;
            }
        }
    }

    public void registrarDevolucao(Long emprestimoId) throws SQLException {

        String buscarEmprestimo = """
                SELECT equipamento_id
                FROM emprestimos
                WHERE id = ?
                  AND situacao = 'ATIVO'
                FOR UPDATE
                """;

        String devolverEmprestimo = """
                UPDATE emprestimos
                SET situacao = 'DEVOLVIDO',
                    data_devolucao = CURRENT_TIMESTAMP
                WHERE id = ?
                  AND situacao = 'ATIVO'
                """;

        String liberarEquipamento = """
                UPDATE equipamentos
                SET disponivel = TRUE
                WHERE id = ?
                """;

        try (Connection conexao = fabricaConexao.getConnection()) {
            conexao.setAutoCommit(false);

            try {
                Long equipamentoId;

                // Localiza o empréstimo ativo.
                try (PreparedStatement comando =
                             conexao.prepareStatement(buscarEmprestimo)) {
                    comando.setLong(1, emprestimoId);

                    try (ResultSet resultado = comando.executeQuery()) {
                        if (!resultado.next()) {
                            throw new SQLException(
                                    "Empréstimo ativo não encontrado: "
                                            + emprestimoId);
                        }

                        equipamentoId =
                                resultado.getLong("equipamento_id");
                    }
                }

                // Atualiza a situação e a data da devolução.
                try (PreparedStatement comando =
                             conexao.prepareStatement(devolverEmprestimo)) {
                    comando.setLong(1, emprestimoId);

                    if (comando.executeUpdate() != 1) {
                        throw new SQLException(
                                "Não foi possível registrar a devolução.");
                    }
                }

                // Libera o equipamento.
                try (PreparedStatement comando =
                             conexao.prepareStatement(liberarEquipamento)) {
                    comando.setLong(1, equipamentoId);

                    if (comando.executeUpdate() != 1) {
                        throw new SQLException(
                                "Não foi possível liberar o equipamento.");
                    }
                }

                conexao.commit();

            } catch (SQLException | RuntimeException erro) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    erro.addSuppressed(erroRollback);
                }

                throw erro;
            }
        }
    }
}

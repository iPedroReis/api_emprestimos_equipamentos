package br.com.unisa.avc_equipamentos.repository;

import br.com.unisa.avc_equipamentos.config.ConnectionFactory;
import br.com.unisa.avc_equipamentos.model.Equipamento;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EquipamentoDAO {

    private final ConnectionFactory fabricaConexao;

    public EquipamentoDAO(ConnectionFactory fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    public List<Equipamento> listarTodos() throws SQLException {
        String sql = """
                SELECT id, nome, patrimonio, disponivel
                FROM equipamentos
                ORDER BY id
                """;

        List<Equipamento> equipamentos = new ArrayList<>();

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                equipamentos.add(mapearEquipamento(resultado));
            }
        }

        return equipamentos;
    }

    public Optional<Equipamento> buscarPorId(Long id)
            throws SQLException {

        String sql = """
                SELECT id, nome, patrimonio, disponivel
                FROM equipamentos
                WHERE id = ?
                """;

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearEquipamento(resultado));
                }
            }
        }

        return Optional.empty();
    }

    public Equipamento criar(Equipamento equipamento)
            throws SQLException {

        String sql = """
                INSERT INTO equipamentos (nome, patrimonio, disponivel)
                VALUES (?, ?, ?)
                RETURNING id
                """;

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, equipamento.getNome());
            comando.setString(2, equipamento.getPatrimonio());
            comando.setBoolean(3, equipamento.isDisponivel());

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    equipamento.setId(resultado.getLong("id"));
                    return equipamento;
                }
            }
        }

        throw new SQLException("Não foi possível cadastrar o equipamento.");
    }

    public boolean atualizar(Long id, Equipamento equipamento)
            throws SQLException {

        String sql = """
                UPDATE equipamentos
                SET nome = ?, patrimonio = ?, disponivel = ?
                WHERE id = ?
                """;

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, equipamento.getNome());
            comando.setString(2, equipamento.getPatrimonio());
            comando.setBoolean(3, equipamento.isDisponivel());
            comando.setLong(4, id);

            return comando.executeUpdate() == 1;
        }
    }

    public boolean possuiHistoricoEmprestimo(Long id)
            throws SQLException {

        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM emprestimos
                    WHERE equipamento_id = ?
                ) AS possui_historico
                """;

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getBoolean("possui_historico");
                }
            }
        }

        return false;
    }

    public boolean excluir(Long id) throws SQLException {
        String sql = """
                DELETE FROM equipamentos
                WHERE id = ?
                """;

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setLong(1, id);

            return comando.executeUpdate() == 1;
        }
    }

    private Equipamento mapearEquipamento(ResultSet resultado)
            throws SQLException {

        return new Equipamento(
                resultado.getLong("id"),
                resultado.getString("nome"),
                resultado.getString("patrimonio"),
                resultado.getBoolean("disponivel")
        );
    }
}

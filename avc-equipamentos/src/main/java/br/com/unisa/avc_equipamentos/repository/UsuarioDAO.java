
package br.com.unisa.avc_equipamentos.repository;

import br.com.unisa.avc_equipamentos.config.ConnectionFactory;
import br.com.unisa.avc_equipamentos.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAO {

    private final ConnectionFactory fabricaConexao;

    public UsuarioDAO(ConnectionFactory fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = """
                SELECT id, nome, matricula
                FROM usuarios
                ORDER BY id
                """;

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                usuarios.add(mapearUsuario(resultado));
            }
        }

        return usuarios;
    }

    public Optional<Usuario> buscarPorId(Long id) throws SQLException {
        String sql = """
                SELECT id, nome, matricula
                FROM usuarios
                WHERE id = ?
                """;

        try (Connection conexao = fabricaConexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearUsuario(resultado));
                }
            }
        }

        return Optional.empty();
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        return new Usuario(
                resultado.getLong("id"),
                resultado.getString("nome"),
                resultado.getString("matricula")
        );
    }
}

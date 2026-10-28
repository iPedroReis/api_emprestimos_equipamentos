
package br.com.unisa.avc_equipamentos.service;

import br.com.unisa.avc_equipamentos.model.Usuario;
import br.com.unisa.avc_equipamentos.repository.UsuarioDAO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    public Optional<Usuario> buscarPorId(Long id) throws SQLException {
        return usuarioDAO.buscarPorId(id);
    }
}

package br.com.unisa.avc_equipamentos.controller;

import br.com.unisa.avc_equipamentos.dto.UsuarioResponseDTO;
import br.com.unisa.avc_equipamentos.exception.RecursoNaoEncontradoException;
import br.com.unisa.avc_equipamentos.model.Usuario;
import br.com.unisa.avc_equipamentos.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() throws SQLException {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioResponseDTO> listaDTO = usuarios.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) throws SQLException {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado."));
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }
}
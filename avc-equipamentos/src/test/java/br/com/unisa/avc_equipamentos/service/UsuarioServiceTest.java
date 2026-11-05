package br.com.unisa.avc_equipamentos.service;

import br.com.unisa.avc_equipamentos.repository.UsuarioDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    private UsuarioService usuarioService;

    @BeforeEach
    void configurar() {
        usuarioService = new UsuarioService(usuarioDAO);
    }

    @Test
    void deveRejeitarIdNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.buscarPorId(null)
        );

        verifyNoInteractions(usuarioDAO);
    }

    @Test
    void deveRejeitarIdZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.buscarPorId(0L)
        );

        verifyNoInteractions(usuarioDAO);
    }

    @Test
    void deveRejeitarIdNegativo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.buscarPorId(-1L)
        );

        verifyNoInteractions(usuarioDAO);
    }
}

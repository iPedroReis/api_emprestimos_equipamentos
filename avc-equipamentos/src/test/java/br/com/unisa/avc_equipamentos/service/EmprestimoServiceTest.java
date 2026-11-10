package br.com.unisa.avc_equipamentos.service;

import br.com.unisa.avc_equipamentos.model.Emprestimo;
import br.com.unisa.avc_equipamentos.repository.EmprestimoDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @Mock
    private EmprestimoDAO emprestimoDAO;

    @InjectMocks
    private EmprestimoService emprestimoService;

    // TESTES DO REGISTRO DE EMPRÉSTIMO

    @Test
    void registrarEmprestimoComIdsValidosRetornaEmprestimo()
            throws SQLException {

        Emprestimo emprestimoEsperado = new Emprestimo();

        when(emprestimoDAO.registrarEmprestimo(1L, 2L))
                .thenReturn(emprestimoEsperado);

        Emprestimo resultado =
                emprestimoService.registrarEmprestimo(1L, 2L);

        assertSame(emprestimoEsperado, resultado);
        verify(emprestimoDAO).registrarEmprestimo(1L, 2L);
    }

    @Test
    void registrarEmprestimoComUsuarioNuloLancaErro() {

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarEmprestimo(null, 2L)
        );

        assertEquals(
                "O ID do usuário deve ser válido.",
                erro.getMessage()
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarEmprestimoComUsuarioZeroLancaErro() {

        assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarEmprestimo(0L, 2L)
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarEmprestimoComUsuarioNegativoLancaErro() {

        assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarEmprestimo(-1L, 2L)
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarEmprestimoComEquipamentoNuloLancaErro() {

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarEmprestimo(1L, null)
        );

        assertEquals(
                "O ID do equipamento deve ser válido.",
                erro.getMessage()
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarEmprestimoComEquipamentoZeroLancaErro() {

        assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarEmprestimo(1L, 0L)
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarEmprestimoComEquipamentoNegativoLancaErro() {

        assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarEmprestimo(1L, -1L)
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarEmprestimoQuandoDAOLancaSQLExceptionPropagaErro()
            throws SQLException {

        when(emprestimoDAO.registrarEmprestimo(1L, 2L))
                .thenThrow(new SQLException("Falha no banco de dados."));

        SQLException erro = assertThrows(
                SQLException.class,
                () -> emprestimoService.registrarEmprestimo(1L, 2L)
        );

        assertEquals("Falha no banco de dados.", erro.getMessage());
        verify(emprestimoDAO).registrarEmprestimo(1L, 2L);
    }

    // TESTES DO REGISTRO DE DEVOLUÇÃO

    @Test
    void registrarDevolucaoComIdValidoChamaDAO()
            throws SQLException {

        emprestimoService.registrarDevolucao(10L);

        verify(emprestimoDAO).registrarDevolucao(10L);
    }

    @Test
    void registrarDevolucaoComIdNuloLancaErro() {

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarDevolucao(null)
        );

        assertEquals(
                "O ID do empréstimo deve ser válido.",
                erro.getMessage()
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarDevolucaoComIdZeroLancaErro() {

        assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarDevolucao(0L)
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarDevolucaoComIdNegativoLancaErro() {

        assertThrows(
                IllegalArgumentException.class,
                () -> emprestimoService.registrarDevolucao(-1L)
        );

        verifyNoInteractions(emprestimoDAO);
    }

    @Test
    void registrarDevolucaoQuandoDAOLancaSQLExceptionPropagaErro()
            throws SQLException {

        doThrow(new SQLException("Falha ao registrar devolução."))
                .when(emprestimoDAO)
                .registrarDevolucao(10L);

        SQLException erro = assertThrows(
                SQLException.class,
                () -> emprestimoService.registrarDevolucao(10L)
        );

        assertEquals(
                "Falha ao registrar devolução.",
                erro.getMessage()
        );

        verify(emprestimoDAO).registrarDevolucao(10L);
    }
}

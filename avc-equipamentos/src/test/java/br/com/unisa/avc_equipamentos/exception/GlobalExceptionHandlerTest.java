
package br.com.unisa.avc_equipamentos.exception;

import br.com.unisa.avc_equipamentos.dto.ApiErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void configurar() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void deveRetornar400QuandoArgumentoForInvalido() {
        ResponseEntity<ApiErrorDTO> resposta =
                handler.tratarArgumentoInvalido(
                        new IllegalArgumentException("ID inválido")
                );

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals(400, resposta.getBody().getStatus());
        assertEquals("ID inválido", resposta.getBody().getMensagem());
    }

    @Test
    void deveRetornar409QuandoEstadoForInvalido() {
        ResponseEntity<ApiErrorDTO> resposta =
                handler.tratarEstadoInvalido(
                        new IllegalStateException(
                                "Equipamento possui histórico"
                        )
                );

        assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
        assertEquals(409, resposta.getBody().getStatus());
        assertEquals(
                "Equipamento possui histórico",
                resposta.getBody().getMensagem()
        );
    }

    @Test
    void deveRetornar404QuandoRecursoNaoForEncontrado() {
        ResponseEntity<ApiErrorDTO> resposta =
                handler.tratarRecursoNaoEncontrado(
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"
                        )
                );

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertEquals(404, resposta.getBody().getStatus());
        assertEquals(
                "Usuário não encontrado",
                resposta.getBody().getMensagem()
        );
    }

    @Test
    void deveRetornar400QuandoRegraDeNegocioForViolada() {
        ResponseEntity<ApiErrorDTO> resposta =
                handler.tratarRegraNegocio(
                        new RegraNegocioException("Regra de negócio violada")
                );

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals(400, resposta.getBody().getStatus());
        assertEquals(
                "Regra de negócio violada",
                resposta.getBody().getMensagem()
        );
    }

    @Test
    void deveRetornar500QuandoOcorrerErroNoBanco() {
        ResponseEntity<ApiErrorDTO> resposta =
                handler.tratarErroBanco(
                        new java.sql.SQLException("Erro interno")
                );

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                resposta.getStatusCode()
        );
        assertEquals(500, resposta.getBody().getStatus());
        assertEquals(
                "Ocorreu um erro ao comunicar com a base de dados.",
                resposta.getBody().getMensagem()
        );
    }

    @Test
    void deveRetornar500QuandoOcorrerErroGenerico() {
        ResponseEntity<ApiErrorDTO> resposta =
                handler.tratarErroGenerico(
                        new Exception("Erro inesperado")
                );

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                resposta.getStatusCode()
        );
        assertEquals(500, resposta.getBody().getStatus());
        assertEquals(
                "Ocorreu um erro interno inesperado no servidor.",
                resposta.getBody().getMensagem()
        );
    }
}

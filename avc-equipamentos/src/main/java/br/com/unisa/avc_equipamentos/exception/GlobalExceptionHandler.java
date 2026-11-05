package br.com.unisa.avc_equipamentos.exception;

import br.com.unisa.avc_equipamentos.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorDTO> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrorDTO> tratarRegraNegocio(RegraNegocioException ex) {
        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> tratarArgumentoInvalido(
            IllegalArgumentException ex) {

        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorDTO> tratarEstadoInvalido(
            IllegalStateException ex) {

        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiErrorDTO> tratarErroBanco(SQLException ex) {
        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro de Banco de Dados",
                "Ocorreu um erro ao comunicar com a base de dados."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> tratarErroGenerico(Exception ex) {
        ApiErrorDTO erro = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocorreu um erro interno inesperado no servidor."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
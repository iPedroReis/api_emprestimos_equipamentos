
package br.com.unisa.avc_equipamentos.controller;

import br.com.unisa.avc_equipamentos.dto.EmprestimoResponseDTO;
import br.com.unisa.avc_equipamentos.dto.EmprestimoRequestDTO;
import br.com.unisa.avc_equipamentos.model.Emprestimo;
import br.com.unisa.avc_equipamentos.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarEmprestimo(
            @RequestBody EmprestimoRequestDTO requisicao)
            throws SQLException {

        if (requisicao == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", "O corpo da requisição é obrigatório."));
        }

        Emprestimo emprestimo =
                emprestimoService.registrarEmprestimo(
                        requisicao.getUsuarioId(),
                        requisicao.getEquipamentoId());

        return ResponseEntity.status(HttpStatus.CREATED)
        .body(new EmprestimoResponseDTO(emprestimo));
    }

    @PatchMapping("/{id}/devolucao")
    public ResponseEntity<Void> registrarDevolucao(
            @PathVariable Long id) throws SQLException {

        emprestimoService.registrarDevolucao(id);

        return ResponseEntity.noContent().build();
    }
}

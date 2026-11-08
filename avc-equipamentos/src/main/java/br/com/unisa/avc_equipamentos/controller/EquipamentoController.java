package br.com.unisa.avc_equipamentos.controller;

import br.com.unisa.avc_equipamentos.model.Equipamento;
import br.com.unisa.avc_equipamentos.service.EquipamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(
            EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> listarTodos()
            throws SQLException {

        return ResponseEntity.ok(
                equipamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long id) throws SQLException {

        Optional<Equipamento> equipamento =
                equipamentoService.buscarPorId(id);

        if (equipamento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(equipamento.get());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(
            @RequestBody Equipamento equipamento)
            throws SQLException {

        if (equipamento == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro",
                    "O corpo da requisição é obrigatório."));
        }

        Equipamento cadastrado =
                equipamentoService.cadastrar(equipamento);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody Equipamento equipamento)
            throws SQLException {

        if (equipamento == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro",
                    "O corpo da requisição é obrigatório."));
        }

        boolean atualizado =
                equipamentoService.atualizar(id, equipamento);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(equipamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable Long id) throws SQLException {

        boolean excluido = equipamentoService.excluir(id);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

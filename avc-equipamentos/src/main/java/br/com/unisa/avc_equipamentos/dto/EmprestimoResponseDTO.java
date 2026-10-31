
package br.com.unisa.avc_equipamentos.dto;

import br.com.unisa.avc_equipamentos.model.Emprestimo;

import java.time.LocalDateTime;

public class EmprestimoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long equipamentoId;
    private LocalDateTime dataRegistro;
    private LocalDateTime dataDevolucao;
    private String situacao;

    public EmprestimoResponseDTO(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.dataRegistro = emprestimo.getDataRegistro();
        this.dataDevolucao = emprestimo.getDataDevolucao();
        this.situacao = emprestimo.getSituacao();

        if (emprestimo.getUsuario() != null) {
            this.usuarioId = emprestimo.getUsuario().getId();
        }

        if (emprestimo.getEquipamento() != null) {
            this.equipamentoId = emprestimo.getEquipamento().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public String getSituacao() {
        return situacao;
    }
}

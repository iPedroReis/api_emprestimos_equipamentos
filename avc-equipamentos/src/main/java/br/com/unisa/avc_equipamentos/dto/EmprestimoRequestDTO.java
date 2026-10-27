
package br.com.unisa.avc_equipamentos.dto;

public class EmprestimoRequestDTO {

    private Long usuarioId;
    private Long equipamentoId;

    public EmprestimoRequestDTO() {
    }

    public EmprestimoRequestDTO(Long usuarioId, Long equipamentoId) {
        this.usuarioId = usuarioId;
        this.equipamentoId = equipamentoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(Long equipamentoId) {
        this.equipamentoId = equipamentoId;
    }
}

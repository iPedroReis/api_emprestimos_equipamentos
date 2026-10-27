
package br.com.unisa.avc_equipamentos.model;

import java.time.LocalDateTime;

public class Emprestimo {

    private Long id;
    private Usuario usuario;
    private Equipamento equipamento;
    private LocalDateTime dataRegistro;
    private LocalDateTime dataDevolucao;
    private String situacao;

    public Emprestimo() {
    }

    public Emprestimo(
            Long id,
            Usuario usuario,
            Equipamento equipamento,
            LocalDateTime dataRegistro,
            LocalDateTime dataDevolucao,
            String situacao) {
        this.id = id;
        this.usuario = usuario;
        this.equipamento = equipamento;
        this.dataRegistro = dataRegistro;
        this.dataDevolucao = dataDevolucao;
        this.situacao = situacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}

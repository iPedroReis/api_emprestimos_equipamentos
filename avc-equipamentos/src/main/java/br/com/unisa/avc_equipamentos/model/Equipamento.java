
package br.com.unisa.avc_equipamentos.model;

public class Equipamento {

    private Long id;
    private String nome;
    private String patrimonio;
    private boolean disponivel;

    public Equipamento() {
    }

    public Equipamento(
            Long id,
            String nome,
            String patrimonio,
            boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.patrimonio = patrimonio;
        this.disponivel = disponivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}

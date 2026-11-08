package br.com.unisa.avc_equipamentos.dto;

import br.com.unisa.avc_equipamentos.model.Usuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String matricula;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nome, String matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public UsuarioResponseDTO(Usuario usuario) {
        if (usuario != null) {
            this.id = usuario.getId();
            this.nome = usuario.getNome();
            this.matricula = usuario.getMatricula();
        }
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
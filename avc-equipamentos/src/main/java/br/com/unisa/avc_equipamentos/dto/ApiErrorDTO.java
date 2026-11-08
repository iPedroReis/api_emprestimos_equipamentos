package br.com.unisa.avc_equipamentos.dto;

import java.time.LocalDateTime;

public class ApiErrorDTO {

    private LocalDateTime dataHora;
    private int status;
    private String erro;
    private String mensagem;

    public ApiErrorDTO() {
    }

    public ApiErrorDTO(int status, String erro, String mensagem) {
        this.dataHora = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
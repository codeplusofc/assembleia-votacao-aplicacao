package com.pauta.aplicacao.dto;

// Campos opcionais recebidos no corpo do PATCH /usuarios/{id}.
public class UsuarioAtualizacaoRequest {
    private String nome;
    private Integer idade;
    private String cpf;
    private String email;
    private String rua;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public boolean isEmpty() {
        return nome == null && idade == null && cpf == null && email == null && rua == null;
    }
}

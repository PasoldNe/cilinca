package com.senai.cilinca.dtos;

public class PacienteEntradaDto {
    private String nome;
    private String email;

    public PacienteEntradaDto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

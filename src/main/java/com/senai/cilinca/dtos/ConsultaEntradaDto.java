package com.senai.cilinca.dtos;

import com.senai.cilinca.models.PacienteEntity;

import java.sql.Timestamp;

public class ConsultaEntradaDto {

    private String titulo;
    private Timestamp data;
    private String status;
    private PacienteEntity pacienteId;

    public ConsultaEntradaDto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PacienteEntity getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(PacienteEntity pacienteId) {
        this.pacienteId = pacienteId;
    }
}

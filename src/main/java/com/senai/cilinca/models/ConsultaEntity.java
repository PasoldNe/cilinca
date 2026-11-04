package com.senai.cilinca.models;

import jakarta.persistence.*;
import com.senai.cilinca.models.PacienteEntity;
import java.sql.Timestamp;

@Entity
public class ConsultaEntity {

    public ConsultaEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    private Timestamp data;

    //@GeneratedValue()//TODO -- Criar ja em AGENDADA
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity pacienteId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

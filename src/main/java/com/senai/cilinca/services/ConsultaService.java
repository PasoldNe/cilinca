package com.senai.cilinca.services;

import com.senai.cilinca.dtos.ConsultaDto;
import com.senai.cilinca.dtos.ConsultaEntradaDto;
import com.senai.cilinca.models.ConsultaEntity;
import com.senai.cilinca.models.PacienteEntity;
import com.senai.cilinca.repositories.ConsultaRepository;
import com.senai.cilinca.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    private final PacienteRepository pacienteRepository;
    public ConsultaService(ConsultaRepository repository, PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.pacienteRepository = pacienteRepository;
    }

    private Boolean dadosInvalidos(ConsultaEntradaDto entrada){
        String titulo = entrada.getTitulo();
        if (titulo == null || titulo.isBlank() || entrada.getData() == null || entrada.getHora() == null
                || entrada.getPacienteId() == null){
            return true;
        }

        String status = entrada.getStatus();
        return !("AGENDADA".equals(status) || "EM_ANDAMENTO".equals(status)
                || "CONCLUIDA".equals(status) || "CANCELADA".equals(status));
    }

    public String cadastrarConsulta(ConsultaEntradaDto entrada){
        if (dadosInvalidos(entrada)){
            return "dadosInvalidos";
        }

        Optional<PacienteEntity> pacienteOP = pacienteRepository.findById(entrada.getPacienteId());
        if (!pacienteOP.isPresent()){
            return "pacienteNaoEncontrado";
        }

        //TODO - validar conflito de consultas ativas no mesmo minuto
        ConsultaEntity entity = new ConsultaEntity();
        entity.setData(entrada.getData());
        entity.setHora(entrada.getHora());
        entity.setPacienteId(pacienteOP.get());
        entity.setStatus(entrada.getStatus());
        entity.setTitulo(entrada.getTitulo());
        repository.save(entity);

        return "Sucesso";
    }

    public String atualizarConsulta(ConsultaEntradaDto entrada, Long id){
        if (dadosInvalidos(entrada)){
            return "dadosInvalidos";
        }

        Optional<ConsultaEntity> consultaOP = repository.findById(id);
        if (!consultaOP.isPresent()){
            return "consultaNaoEncontrada";
        }

        Optional<PacienteEntity> pacienteOP = pacienteRepository.findById(entrada.getPacienteId());
        if (!pacienteOP.isPresent()){
            return "pacienteNaoEncontrado";
        }

        //TODO - validar conflito sem comparar a consulta com ela mesma
        ConsultaEntity consulta = consultaOP.get();
        consulta.setTitulo(entrada.getTitulo());
        consulta.setData(entrada.getData());
        consulta.setHora(entrada.getHora());
        consulta.setStatus(entrada.getStatus());
        consulta.setPacienteId(pacienteOP.get());
        repository.save(consulta);
        return "Sucesso";
    }

    //true - DELETADO    false - NAO deletado
    public Boolean deletarConsulta(Long id){
        Optional<ConsultaEntity> consultaOP = repository.findById(id);

        if (consultaOP.isPresent()){
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //retorna lista mesmo q vazia
    public List<ConsultaDto> listarConsultas(Long pacienteId){
        List<ConsultaEntity> listaConsulta = repository.findAll();
        List<ConsultaDto> listaRetorno = new ArrayList<>();

        for (ConsultaEntity consulta : listaConsulta){
            Long idPacienteConsulta = consulta.getPacienteId().getId();
            if (pacienteId == null || pacienteId.equals(idPacienteConsulta)){
                ConsultaDto retorno = new ConsultaDto();
                retorno.setData(consulta.getData());
                retorno.setHora(consulta.getHora());
                retorno.setId(consulta.getId());
                retorno.setPacienteId(idPacienteConsulta);
                retorno.setStatus(consulta.getStatus());
                retorno.setTitulo(consulta.getTitulo());
                listaRetorno.add(retorno);
            }
        }
        return listaRetorno;
    }
}

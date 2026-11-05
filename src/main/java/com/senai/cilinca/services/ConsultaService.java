package com.senai.cilinca.services;

import com.senai.cilinca.dtos.ConsultaDto;
import com.senai.cilinca.dtos.ConsultaEntradaDto;
import com.senai.cilinca.dtos.PacienteDto;
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

    //- procurar paciente  FINDBYID
    //- cadastrar consulta vinculada a um paciente
    //- atualizar consultas
    //TODO - deletar consultas

    //ele ira procurar e validar se ele existe
    public Boolean validadorDePaciente(Long pacienteId){
        Optional<PacienteEntity> pacienteOP = pacienteRepository.findById(pacienteId);
        if (pacienteOP.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    //true - SUCESSO    false - ERRO
    public Boolean cadastrarConsulta(ConsultaEntradaDto entrada){
        ConsultaEntity entity = new ConsultaEntity();

        entity.setData(entrada.getData());
        entity.setPacienteId(entrada.getPacienteId());
        entity.setStatus(entrada.getStatus());
        entity.setTitulo(entrada.getTitulo());
        repository.save(entity);

        return true;
    }

    //atualizar com todos os dados novos a partir de um titulo existente
    //true - ATUALIZADO    false - NAO atualizado
    public Boolean atualizarConsulta(ConsultaDto entrada, Long id){
        List<ConsultaEntity> listaConsulta = repository.findAll();

        for (ConsultaEntity consulta : listaConsulta){
            if (id == entrada.getId()){
                consulta.setTitulo(entrada.getTitulo());
                consulta.setData(entrada.getData());
                consulta.setStatus(entrada.getStatus());
                consulta.setPacienteId(entrada.getPacienteId());
                repository.save(consulta);
                return true;
            }
        }
        return false;
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
    public List<ConsultaDto> listarConsultas(){
        List<ConsultaEntity> listaConsulta = repository.findAll();
        List<ConsultaDto> listaRetorno = new ArrayList<>();

        for (ConsultaEntity consulta : listaConsulta){
            ConsultaDto retorno = new ConsultaDto();
            retorno.setData(consulta.getData());
            retorno.setId(consulta.getId());
            retorno.setPacienteId(consulta.getPacienteId());
            retorno.setStatus(consulta.getStatus());
            retorno.setTitulo(consulta.getTitulo());
            listaRetorno.add(retorno);
        }
        return listaRetorno;
    }
}

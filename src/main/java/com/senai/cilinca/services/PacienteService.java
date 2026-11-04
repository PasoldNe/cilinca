package com.senai.cilinca.services;

import com.senai.cilinca.dtos.PacienteDto;
import com.senai.cilinca.dtos.PacienteEntradaDto;
import com.senai.cilinca.models.PacienteEntity;
import com.senai.cilinca.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;
    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }


    //emailCadastrado
    //dadosInvalidos
    //sucesso :)
    public String cadastrarPaciente(PacienteEntradaDto entrada){
        PacienteEntity entity = new PacienteEntity();
        List<PacienteEntity> lista = repository.findAll();


        if (entrada.getNome().equals("") || entrada.getEmail().equals("")){
            return "dadosInvalidos";
        }
        for (PacienteEntity paciente : lista){
            if(paciente.getEmail().equals(entrada.getEmail())){
                return "emailCadastrado";
            }
        }
        entity.setEmail(entrada.getEmail());
        entity.setNome(entrada.getNome());
        repository.save(entity);

        return "Sucesso :)";
    }

    public List<PacienteDto> listarPaciente(){
        //criar lista de dto vazia
        //buscar todos o pacienteentity
        //copiar os dados dos entitys para os dtos
        //adicionar a lista
        //retornar
        List<PacienteDto> lista = new ArrayList<>();
        List<PacienteEntity> listaEntity = repository.findAll();

        for (PacienteEntity entity : listaEntity){
            PacienteDto paciente = new PacienteDto();
            paciente.setEmail(entity.getEmail());
            paciente.setNome(entity.getNome());
            paciente.setId(entity.getId());
            lista.add(paciente);
        }
        return lista;
    }

    //true - ATUALIZADO            false - NAO atualizado
    public Boolean atualizarPaciente(PacienteDto entrada, String email){
        //obter todos os pacientes existentes
        //procurar pelo paciente
        //atualizar os dados
        //retornar paciente inteiro
        List<PacienteEntity> listaEntity = repository.findAll();

        for (PacienteEntity paciente : listaEntity){
            if(paciente.getEmail().equals(entrada.getEmail())){
                return false;

            }
        }

        for (PacienteEntity paciente : listaEntity){
            if (paciente.getEmail().equals(email)){
                paciente.setNome(entrada.getNome());
                paciente.setEmail(entrada.getEmail());
                repository.save(paciente);
                return true;
            }
        }
        return false;
    }

    //true - DELETADO    false - NAO deletado
    public Boolean deletarPaciente(String email){
        List<PacienteEntity> listaEntity = repository.findAll();

        for (PacienteEntity paciente : listaEntity){
            if (paciente.getEmail().equals(email)){
                repository.delete(paciente);
                return true;
            }
        }
        return false;
    }

}

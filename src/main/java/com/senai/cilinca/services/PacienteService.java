package com.senai.cilinca.services;

import com.senai.cilinca.dtos.PacienteDto;
import com.senai.cilinca.dtos.PacienteEntradaDto;
import com.senai.cilinca.models.ConsultaEntity;
import com.senai.cilinca.models.PacienteEntity;
import com.senai.cilinca.repositories.ConsultaRepository;
import com.senai.cilinca.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;
    private final ConsultaRepository consultaRepository;
    public PacienteService(PacienteRepository repository, ConsultaRepository consultaRepository) {
        this.repository = repository;
        this.consultaRepository = consultaRepository;
    }


    //emailCadastrado
    //dadosInvalidos
    //sucesso :)
    public String cadastrarPaciente(PacienteEntradaDto entrada){
        if (dadosInvalidos(entrada)){
            return "dadosInvalidos";
        }
        if (procurarPaciente(entrada.getEmail()) != null){
            return "emailCadastrado";
        }

        PacienteEntity entity = new PacienteEntity();
        entity.setEmail(entrada.getEmail().strip());
        entity.setNome(entrada.getNome().strip());
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

    public PacienteEntity procurarPaciente(String email){
        List<PacienteEntity> listaPaciente = repository.findAll();
        String emailLimpo = email.strip();

        for (PacienteEntity paciente : listaPaciente){
            if (emailLimpo.equalsIgnoreCase(paciente.getEmail().strip())){
                return paciente;
            }
        }
        return null;
    }

    private Boolean dadosInvalidos(PacienteEntradaDto entrada){
        if (entrada.getNome() == null || entrada.getNome().isBlank() || entrada.getEmail() == null){
            return true;
        }

        String email = entrada.getEmail().strip();
        int arroba = email.indexOf("@");
        int ponto = email.lastIndexOf(".");
        return arroba < 1 || arroba != email.lastIndexOf("@")
                || ponto <= arroba + 1 || ponto == email.length() - 1 || email.contains(" ");
    }

    public String atualizarPaciente(PacienteEntradaDto entrada, String email){
        if (dadosInvalidos(entrada)){
            return "dadosInvalidos";
        }

        PacienteEntity paciente = procurarPaciente(email);
        if (paciente == null){
            return "pacienteNaoEncontrado";
        }

        PacienteEntity outroPaciente = procurarPaciente(entrada.getEmail());
        if (outroPaciente != null && !outroPaciente.getId().equals(paciente.getId())){
            return "emailCadastrado";
        }

        paciente.setNome(entrada.getNome().strip());
        paciente.setEmail(entrada.getEmail().strip());
        repository.save(paciente);
        return "Atualizado";
    }

    public String deletarPaciente(String email){
        PacienteEntity paciente = procurarPaciente(email);
        if (paciente == null){
            return "pacienteNaoEncontrado";
        }

        List<ConsultaEntity> listaConsulta = consultaRepository.findAll();
        for (ConsultaEntity consulta : listaConsulta){
            if (paciente.getId().equals(consulta.getPacienteId().getId())){
                return "pacienteVinculado";
            }
        }

        repository.delete(paciente);
        return "Sucesso";
    }

}

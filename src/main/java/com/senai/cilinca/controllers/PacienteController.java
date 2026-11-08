package com.senai.cilinca.controllers;

import com.senai.cilinca.dtos.PacienteDto;
import com.senai.cilinca.dtos.PacienteEntradaDto;
import com.senai.cilinca.services.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService service;
    public PacienteController(PacienteService service) {
        this.service = service;
    }

    //http://localhost:8080/paciente
    @PostMapping
    public ResponseEntity<String> cadastrarPaciente(@RequestBody PacienteEntradaDto entrada){
        String metodo = service.cadastrarPaciente(entrada);
        if (metodo.equals("dadosInvalidos")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados invalidos :(");
        }
        //email ja cadastrado
        if (metodo.equals("emailCadastrado")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail ja cadastrado :/");
        }

        return ResponseEntity.ok().body(metodo);
    }

    //http://localhost:8080/paciente
    @GetMapping
    public ResponseEntity<List<PacienteDto>> listarPaciente(){
        List<PacienteDto> lista = service.listarPaciente();
        return ResponseEntity.ok().body(lista);
    }

    //http://localhost:8080/paciente/[email]
    @PutMapping("/{email}")
    public ResponseEntity<String> atualizarCadastro(@RequestBody PacienteEntradaDto entrada, @PathVariable String email){
        String metodo = service.atualizarPaciente(entrada, email);
        if (metodo.equals("dadosInvalidos")){
            return ResponseEntity.badRequest().body("Dados invalidos :(");
        }
        if (metodo.equals("emailCadastrado")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ja cadastrado a outro paciente :(");
        }
        if (metodo.equals("pacienteNaoEncontrado")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente nao encontrado");
        }
        return ResponseEntity.ok().body(metodo);
    }

    //http://localhost:8080/paciente/{email}
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String email){
        String metodo = service.deletarPaciente(email);
        if (metodo.equals("pacienteNaoEncontrado")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente nao encontrado");
        }
        if (metodo.equals("pacienteVinculado")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Paciente possui consultas");
        }
        return ResponseEntity.ok().body(metodo);
    }
}

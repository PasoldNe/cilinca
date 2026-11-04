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
        //dados invalidos
        if (service.cadastrarPaciente(entrada) == "dadosInvalidos"){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados invalidos :(");
        }
        //email ja cadastrado
        if (service.cadastrarPaciente(entrada) == "emailCadastrado"){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("E-mail ja cadastrado :/");
        }

        return ResponseEntity.status(HttpStatus.OK).body(service.cadastrarPaciente(entrada));
    }

    //http://localhost:8080/paciente
    @GetMapping
    public ResponseEntity<List<PacienteDto>> listarPaciente(){
        return ResponseEntity.ok().body(service.listarPaciente());
    }

    //http://localhost:8080/paciente/[email]
    @PutMapping("/{email}")
    public ResponseEntity<String> atualizarCadastro(@RequestBody PacienteDto entrada , @PathVariable String email){
        if (service.atualizarPaciente(entrada, email)){
            return ResponseEntity.ok().body("Atualizado");
        } else {
            return ResponseEntity.badRequest().body("Nao encontrado");
        }
    }

    //http://localhost:8080/paciente/{email}
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String email){
        //chamar o service
        if (service.deletarPaciente(email)){
            return ResponseEntity.ok().body("Sucesso");
        }
        return ResponseEntity.badRequest().body("Fracasso");
    }
}

package com.senai.cilinca.controllers;

import com.senai.cilinca.dtos.ConsultaDto;
import com.senai.cilinca.dtos.ConsultaEntradaDto;
import com.senai.cilinca.services.ConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultaController {

    private final ConsultaService service;
    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping("/consulta")
    public ResponseEntity<String> cadastrarConsulta(@RequestBody ConsultaEntradaDto entrada){
        String metodo = service.cadastrarConsulta(entrada);
        if (metodo.equals("dadosInvalidos")){
            return ResponseEntity.badRequest().body("Dados invalidos :(");
        }
        if (metodo.equals("pacienteNaoEncontrado")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente nao encontrado");
        }
        return ResponseEntity.ok().body(metodo);
    }

    @GetMapping({"/consultas", "/consulta", "/consultas/paciente/{pacienteId}"})
    public ResponseEntity<List<ConsultaDto>> listarConsulta(@PathVariable(required = false) Long pacienteId){
        return ResponseEntity.ok().body(service.listarConsultas(pacienteId));
    }

    @PutMapping("/consulta/{id}")
    public ResponseEntity<String> atualizarConsulta(@RequestBody ConsultaEntradaDto entrada, @PathVariable Long id){
        String metodo = service.atualizarConsulta(entrada, id);
        if (metodo.equals("dadosInvalidos")){
            return ResponseEntity.badRequest().body("Dados invalidos :(");
        }
        if (metodo.equals("consultaNaoEncontrada")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta nao encontrada");
        }
        if (metodo.equals("pacienteNaoEncontrado")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente nao encontrado");
        }
        return ResponseEntity.ok().body(metodo);
    }

    @DeleteMapping("/consulta/{id}")
    public ResponseEntity<String> deletarConsulta(@PathVariable Long id){
        if (service.deletarConsulta(id)){
            return ResponseEntity.ok().body("Sucesso, excluido!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fracasso, nao encontrado :(");
        }
    }
}

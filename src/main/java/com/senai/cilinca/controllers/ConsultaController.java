package com.senai.cilinca.controllers;

import com.senai.cilinca.dtos.ConsultaDto;
import com.senai.cilinca.dtos.ConsultaEntradaDto;
import com.senai.cilinca.services.ConsultaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    private final ConsultaService service;
    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarConsulta(ConsultaEntradaDto entrada){
        Boolean metodo = service.cadastrarConsulta(entrada);
        if (metodo){
            return ResponseEntity.ok().body("Sucesso");
        }else{
            return ResponseEntity.badRequest().body("Fracasso");
        }
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDto>> listarConsulta(){
        return ResponseEntity.ok().body(service.listarConsultas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarConsulta(@RequestBody ConsultaDto entrada, @PathVariable Long id){
        if (service.atualizarConsulta(entrada, id)){
            return ResponseEntity.ok().body("Sucesso");
        } else {
            return ResponseEntity.badRequest().body("Fracasso");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarConsulta(@PathVariable Long id){
        if (service.deletarConsulta(id)){
            return ResponseEntity.ok().body("Sucesso, excluido!");
        } else {
            return ResponseEntity.badRequest().body("Fracasso, nao encontrado :(");
        }
    }
}

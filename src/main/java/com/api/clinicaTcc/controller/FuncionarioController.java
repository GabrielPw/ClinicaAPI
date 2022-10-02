package com.api.clinicaTcc.controller;


import com.api.clinicaTcc.model.Funcionario;
import com.api.clinicaTcc.repository.FuncionarioRepository;
import com.api.clinicaTcc.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/funcionarios")
public class FuncionarioController {


    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Funcionario>  novoFuncionario(@RequestBody Funcionario funcionario){

        return ResponseEntity.ok(funcionarioService.novoFuncionario(funcionario));
    }
}

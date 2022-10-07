package com.api.clinicaTcc.controller;

import com.api.clinicaTcc.model.Cliente;
import com.api.clinicaTcc.repository.ClienteRepository;
import com.api.clinicaTcc.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Cliente>> obterTodos(){


        return ResponseEntity.ok(clienteService.obterTodos());
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Cliente> obterPorId(@PathVariable Long id){

        Optional<Cliente> existe = clienteService.obterPorId(id);

        if(existe.isPresent()){

            return ResponseEntity.ok(existe.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente){


        return ResponseEntity.ok(clienteService.cadastrar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente){


        Optional<Cliente> existe = clienteService.atualizar(id, cliente);

        if (!existe.isPresent()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluirPorId(@PathVariable Long id){

        boolean existe = clienteService.excluirPorId(id);

        if (existe){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/atualizarTelefone/{id}")
    public ResponseEntity<Cliente> atualizarNumeroTelefone(@PathVariable Long id, @RequestBody Cliente cliente){

        Optional<Cliente> existe = clienteService.atualizarNumeroTelefone(id, cliente);

        if (existe.isPresent()){
            return ResponseEntity.ok(existe.get());
        }
        return ResponseEntity.notFound().build();
    }

}

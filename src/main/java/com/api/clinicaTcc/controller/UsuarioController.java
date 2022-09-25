package com.api.clinicaTcc.controller;

import com.api.clinicaTcc.model.Cliente;
import com.api.clinicaTcc.model.Usuario;
import com.api.clinicaTcc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> getAll(){

        return ResponseEntity.ok(usuarioService.obterTodos());
    }

    @GetMapping("/getByCpf/{cpf}")
    public ResponseEntity<Usuario> getByCpf(@PathVariable String cpf){

        System.out.println("\n\niniciando busca...");
        Optional<Usuario> user = usuarioService.obterPorCpf(cpf);

        if (user.isPresent()){
            System.out.println("Está presente!!");
            return ResponseEntity.ok(user.get());
        }

        System.out.println("\n\nCPF inserido não foi encontrado.");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/newUsuario")
    public ResponseEntity<Usuario> newUsuario(@RequestBody Usuario usuario){


        return ResponseEntity.ok(usuarioService.novoUsuario(usuario));
    }


}

package com.api.clinicaTcc.controller;

import com.api.clinicaTcc.model.Cliente;
import com.api.clinicaTcc.model.Usuario;
import com.api.clinicaTcc.service.UsuarioService;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Usuario>> getAll(){

        return ResponseEntity.ok(usuarioService.obterTodos());
    }

    @GetMapping("/getByCpf/{cpf}")
    public ResponseEntity getByCpf(@PathVariable("cpf") @CPF(message = "CPF INVALIDO.") String cpf){

        System.out.println("\n\niniciando busca...");
        Optional<Usuario> user = usuarioService.obterPorCpf(cpf);

        if (user.isPresent()){
            System.out.println("Está presente!!");
            return ResponseEntity.ok(user.get());
        }

        System.out.println("\n\nCPF inserido não foi encontrado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inserido não foi encontrado");
    }

    @PostMapping("/newUsuario")
    public ResponseEntity<Usuario> newUsuario(@Valid @RequestBody Usuario usuario){


        return ResponseEntity.ok(usuarioService.novoUsuario(usuario));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExcception(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleValidationConstraintExcception(ConstraintViolationException ex){

        Map<String, String> errors = new HashMap<>();

        //System.out.println("Constraint Violations: (CPF INVÁliDO?) ==>" + ex.getConstraintViolations().toString().contains("CPF INVALIDO"));

        if (ex.getConstraintViolations().toString().contains("CPF INVALIDO")){
            return "CPF INSERIDO É INVÁLIDO";
        }

        return "Erro";
    }



}

package com.api.clinicaTcc.service;

import com.api.clinicaTcc.controller.ClienteController;
import com.api.clinicaTcc.controller.UsuarioController;
import com.api.clinicaTcc.model.Cliente;
import com.api.clinicaTcc.model.Usuario;
import com.api.clinicaTcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteController clienteController;

    public List<Usuario> obterTodos(){

        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obterPorCpf(String cpf){

        Optional<Usuario> usuario = usuarioRepository.findById(cpf);
        return usuario;
    }
    public Usuario novoUsuario(Usuario usuario){

        String cpf = usuario.getCpf();

        Optional<Usuario> existe = usuarioRepository.findById(cpf);

        if (existe.isPresent()){
            throw new IllegalArgumentException("O CPF inserido já está cadastrado.");
        } else {

            usuarioRepository.save(usuario);
            return usuario;
        }
    }

    public boolean excluirPorCpf(String cpf){

        boolean existe = usuarioRepository.existsById(cpf);

        if (existe){
            usuarioRepository.delete(usuarioRepository.findById(cpf).get());
        }

        return existe;
    }


}

package com.api.clinicaTcc.service;

import com.api.clinicaTcc.model.Cliente;
import com.api.clinicaTcc.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> obterTodos(){

        return clienteRepository.findAll();
    }

    public Optional<Cliente> obterPorId(Long id){

        return clienteRepository.findById(id);
    }

    public void cadastrar(Cliente cliente){

        clienteRepository.save(cliente);
    }

    public Optional<Cliente> atualizar(Long id, Cliente cliente){

        Optional<Cliente> existe = clienteRepository.findById(id);

        if (!existe.isPresent()){

            return Optional.empty();
        }

        cliente.setId(id);
        return Optional.of(clienteRepository.save(cliente));
    }

    public boolean excluirPorId(Long id){

        boolean exists = clienteRepository.existsById(id);

        if (exists){
            clienteRepository.delete(clienteRepository.findById(id).get());
        }

        return exists;
    }

    public Optional<Cliente> atualizarNumeroTelefone(Long id, Cliente cliente){

        Optional<Cliente> existe = clienteRepository.findById(id);

        if(existe.isPresent()){
            String novoNumero = cliente.getTelefone();
            clienteRepository.updatePhone(id, novoNumero);

            return existe;
        }

        return existe;
    }


}

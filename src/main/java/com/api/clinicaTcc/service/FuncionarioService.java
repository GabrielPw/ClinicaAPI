package com.api.clinicaTcc.service;

import com.api.clinicaTcc.model.Funcionario;
import com.api.clinicaTcc.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public Funcionario novoFuncionario(Funcionario funcionario){

        funcionarioRepository.save(funcionario);

        return funcionario;
    }
}

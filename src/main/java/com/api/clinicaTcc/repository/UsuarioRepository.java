package com.api.clinicaTcc.repository;

import com.api.clinicaTcc.model.Cliente;
import com.api.clinicaTcc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    boolean existsByCpfAndSenha(String cpf, String senha);

}

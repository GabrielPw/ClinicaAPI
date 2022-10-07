package com.api.clinicaTcc.model;

import com.api.clinicaTcc.model.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "usuario")

public class Usuario {

    @Id
    @Column(name = "cpf", columnDefinition = "char(11)")


    @CPF(message = "CPF inserido é inválido")
    private String cpf;

    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @OneToOne(mappedBy = "usuario")
    Cliente cliente;

    public Usuario() {
    }

    public Usuario(String cpf, String senha, Cliente cliente) {
        this.cpf = cpf;
        this.senha = senha;
        this.cliente = cliente;
    }
}

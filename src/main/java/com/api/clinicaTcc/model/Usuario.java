package com.api.clinicaTcc.model;

import com.api.clinicaTcc.model.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "cpf", columnDefinition = "char(11)")
    private String cpf;

    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @OneToOne(mappedBy = "usuario")
    Cliente cliente;
}

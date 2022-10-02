package com.api.clinicaTcc.model;
import com.api.clinicaTcc.enums.FuncionarioCargoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "funcionario")
public class Funcionario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    private FuncionarioCargoEnum cargo;

}

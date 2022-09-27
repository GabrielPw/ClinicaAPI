package com.api.clinicaTcc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity(name = "cliente")
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"usuario_cpf"})) // Defini o campo usuario_cpf como unique para poder referencia-lo como chave estrangeira na tabela consulta.
public class Cliente {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    @OneToOne
    private Usuario usuario;

}

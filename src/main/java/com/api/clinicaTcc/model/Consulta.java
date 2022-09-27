package com.api.clinicaTcc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Long valor;

    @Column(name = "data_hora")
    private LocalDateTime data_hora;


    @ManyToOne
    private Clinica clinica;

    @OneToMany
    private List<Servico> servico;

    @ManyToOne
    @JoinColumn(name = "usuario_cpf")
    private Cliente cliente;

}

package com.api.clinicaTcc.model;

import com.api.clinicaTcc.enums.TipoServicoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Long valor;

    @Column(name = "tempo")
    private LocalDateTime tempo;

    @Column(name = "tipo")
    private TipoServicoEnum tipo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "nome")
    private String nome;
}

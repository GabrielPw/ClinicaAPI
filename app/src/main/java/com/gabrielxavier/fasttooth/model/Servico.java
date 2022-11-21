package com.gabrielxavier.fasttooth.model;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Servico implements Serializable {

    // Warning: Está faltando o atributo time (eu o removi para fazer testes sem ele. Tem que colocar ele de volta dps).

    private Integer id;
    private String nome;
    private String descricao;
    private String tipo;
    private String tempo;
    private Double valor;

    public Servico(Integer id, String nome, String descricao, String tipo, String tempo, Double valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.tempo = tempo;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

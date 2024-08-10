package com.fourcamp.NutriPlan.model;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String genero;
    private double peso;
    private double pesoDesejado;
    private double altura;
    private Date dataNascimento;
    private String senha;
    private String categoria;
    private String tempoMeta;

    public Cliente(String nome, String email, String genero, double peso, double pesoDesejado,double altura, Date dataNascimento, String senha, String categoria, String tempoMeta) {
        this.nome = nome;
        this.email = email;
        this.genero = genero;
        this.peso = peso;
        this.pesoDesejado = pesoDesejado;
        this.altura = altura;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
        this.categoria = categoria;
        this.tempoMeta = tempoMeta;
    }
}

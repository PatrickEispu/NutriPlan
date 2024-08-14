package com.fourcamp.NutriPlan.model.conta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Cliente extends ContaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String genero;
    private double peso;
    private double altura;
    private Date dataNascimento;
    private String categoria;

    public Cliente(String nome, String email, String genero, double peso, double pesoDesejado,double altura, Date dataNascimento, String senha, String categoria, String tempoMeta) {
        this.nome = nome;
        this.email = email;
        this.genero = genero;
        this.peso = peso;
     //   this.pesoDesejado = pesoDesejado;
        this.altura = altura;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
        this.categoria = categoria;
//        this.tempoMeta = tempoMeta;
    }
}

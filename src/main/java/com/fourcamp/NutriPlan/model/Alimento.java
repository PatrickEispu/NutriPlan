package com.fourcamp.NutriPlan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double kcal;
    private double carboidrato;
    private double proteina;
    private double gordura;
    private double quantidade;
    private String nome;

    public Alimento(double kcal, double carboidrato, double proteina, double gordura, double quantidade, String nome) {
        this.kcal = kcal;
        this.carboidrato = carboidrato;
        this.proteina = proteina;
        this.gordura = gordura;
        this.quantidade = quantidade;
        this.nome = nome;
    }
}

package com.fourcamp.NutriPlan.model.conta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Cliente extends ContaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConta;
    private char genero;
    private double peso;
    private double altura;
    private String dataNascimento;
    private double tmb;
    private double get;
    private String IdCategoria;
}

package com.fourcamp.NutriPlan.model.conta;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Cliente extends ContaEntity {
    private Integer idConta;
    private char genero;
    private Double peso;
    private Double altura;
    private String dataNascimento;
    private double tmb;
    private double get;
    private int idCategoria;
}
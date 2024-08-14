package com.fourcamp.NutriPlan.model.alimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alimento {

    private Integer idAlimento;
    private Integer idCategoriaAlimento;
    private Double kcal;
    private Double carboidrato;
    private Double proteina;
    private Double gordura;
    private Double quantidade;
    private String nome;

}

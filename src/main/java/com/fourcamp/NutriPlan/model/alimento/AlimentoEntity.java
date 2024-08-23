package com.fourcamp.NutriPlan.model.alimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlimentoEntity {

    private Integer idAlimento;
    private String CategoriaAlimento;
    private Double kcal;
    private Double carboidrato;
    private Double proteina;
    private Double gordura;
    private Double quantidade;
    private String nome;

}

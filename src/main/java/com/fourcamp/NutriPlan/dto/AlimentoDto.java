package com.fourcamp.NutriPlan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AlimentoDto {

    private double kcal;
    private double carboidrato;
    private double proteina;
    private double gordura;
    private double quantidade;
    private String nome;
}

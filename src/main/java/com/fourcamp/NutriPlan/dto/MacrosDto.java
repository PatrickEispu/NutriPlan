package com.fourcamp.NutriPlan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MacrosDto {

    private Double kcalTotais;
    private Double carboidrato;
    private Double proteina;
    private Double gordura;

}

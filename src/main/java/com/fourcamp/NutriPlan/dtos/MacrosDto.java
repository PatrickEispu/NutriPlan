package com.fourcamp.NutriPlan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacrosDto {

    private Double kcalTotais;
    private Double carboidrato;
    private Double proteina;
    private Double gordura;
    private Integer idAlimento;

}

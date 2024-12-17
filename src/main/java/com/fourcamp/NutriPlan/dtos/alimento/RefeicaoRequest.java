package com.fourcamp.NutriPlan.dtos.alimento;

import lombok.Data;

@Data
public class RefeicaoRequest {

    private String alimento;
    private Double quantidade;
}

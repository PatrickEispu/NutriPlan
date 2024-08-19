package com.fourcamp.NutriPlan.dto.alimento;

import lombok.Data;

@Data
public class RefeicaoRequest {

    private String alimento;
    private Double quantidade;
}

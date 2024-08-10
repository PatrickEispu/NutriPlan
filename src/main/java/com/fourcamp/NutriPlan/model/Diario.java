package com.fourcamp.NutriPlan.model;

import lombok.Data;

import java.util.Date;

@Data
public class Diario {

    private String alimento;
    private double quantidade;
    private double kcal;
    private double carboidrato;
    private double proteina;
    private double gordura;
    private Date data;
}

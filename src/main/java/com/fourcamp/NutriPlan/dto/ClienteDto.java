package com.fourcamp.NutriPlan.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ClienteDto {

    private String nome;
    private String email;
    private String genero;
    private double peso;
    private double pesoDesejado;
    private double altura;
    private Date dataNascimento;
    private String senha;
    private String categoria;
    private String tempoMeta;

}

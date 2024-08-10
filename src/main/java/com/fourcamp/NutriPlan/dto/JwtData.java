package com.fourcamp.NutriPlan.dto;

import lombok.Data;

import java.util.Date;
@Data
public class JwtData {

    private String nome;
    private String email;
    private String genero;
    private Double peso;
    private Double pesoDesejado;
    private Double altura;
    private Date dataNascimento;
    private String senha;
    private String categoria;
    private String tempoMeta;
}

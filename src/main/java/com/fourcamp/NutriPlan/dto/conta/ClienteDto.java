package com.fourcamp.NutriPlan.dto.conta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClienteDto extends ContaDto {
    private Integer idConta;
    private char genero;
    private double peso;
    private double altura;
    private String dataNascimento;
    private double tmb;
    private double get;
    private String IdCategoria;
}
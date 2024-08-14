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

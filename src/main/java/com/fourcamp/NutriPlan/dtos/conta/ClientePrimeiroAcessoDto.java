package com.fourcamp.NutriPlan.dto.conta;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClientePrimeiroAcessoDto {
    private Integer idConta;
    private String nome;
    private char genero;
    private Double peso;
    private Double altura;
    private String dataNascimento;
    private Double tmb;
    private Double get;
    private String categoria;
    private String tempo;
    private String objetivo;
    private Integer idCategoria;

}

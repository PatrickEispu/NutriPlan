package com.fourcamp.NutriPlan.dtos.conta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClienteDto extends ContaDto {
    private Integer idConta;
    private String genero;
    private Double peso;
    private Double altura;
    private String dataNascimento;
    private Double tmb;
    private Double get;
    private String Categoria;
}
package com.fourcamp.NutriPlan.model.conta;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClienteEntity extends ContaEntity {
    private String genero;
    private Double peso;
    private Double altura;
    private String dataNascimento;
    private Double tmb;
    private Double get;
    private String nomeCategoria;
    private Integer idConta;
}
package com.fourcamp.NutriPlan.model.conta;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ContaEntity {

    private Integer idConta;
    private String nome;
    private String email;
    private String senha;
    private Integer TipoConta;
}

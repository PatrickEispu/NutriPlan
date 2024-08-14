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
    Integer idConta;
    String nome;
    String email;
    String senha;
    Integer fkIdTipoConta;
}

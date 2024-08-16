package com.fourcamp.NutriPlan.dto.conta;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ContaDto {
    Integer idConta;
    String nome;
    String email;
    String senha;
    String TipoConta;

}

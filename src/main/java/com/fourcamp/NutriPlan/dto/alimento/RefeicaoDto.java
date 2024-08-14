package com.fourcamp.NutriPlan.dto.alimento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RefeicaoDto {
    Integer idRefeicao;
    Integer quantidade;
    String nomeAliemento;
    Integer fkIdAlimento;
}

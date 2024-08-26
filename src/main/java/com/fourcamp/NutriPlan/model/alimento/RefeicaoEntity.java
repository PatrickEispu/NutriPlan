package com.fourcamp.NutriPlan.model.alimento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RefeicaoEntity {
    Integer fkNrIdRefeicao;
    Integer nrQuantidade;
    String nomeAlimento;
    List<AlimentoEntity> alimentoList;
    Integer fkNrIdAlimento;
}

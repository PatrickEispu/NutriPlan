package com.fourcamp.NutriPlan.model.diario;

import com.fourcamp.NutriPlan.model.alimento.RefeicaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DiarioEntity {
    Integer fkIdConta;
    String data;
    List<RefeicaoEntity> refeicao;
    Integer fkIdRefeicao;
}

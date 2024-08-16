package com.fourcamp.NutriPlan.dto.alimento;

import com.fourcamp.NutriPlan.model.alimento.Alimento;
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
   // Alimento alimento;
    String nomeAlimento;
    Integer fkIdAlimento;
}

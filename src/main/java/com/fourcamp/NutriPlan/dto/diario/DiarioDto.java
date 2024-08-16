package com.fourcamp.NutriPlan.dto.diario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DiarioDto {
    Integer fkIdConta;
    String data;
    Integer fkIdRefeicao;

}

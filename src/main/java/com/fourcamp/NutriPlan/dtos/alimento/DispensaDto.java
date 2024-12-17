package com.fourcamp.NutriPlan.dtos.alimento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DispensaDto {
    private Integer fkNrIdDispensa;
    private Integer nrQuantidade;
    private String nomeAlimento;
    private Integer fkNrIdAlimento;

}

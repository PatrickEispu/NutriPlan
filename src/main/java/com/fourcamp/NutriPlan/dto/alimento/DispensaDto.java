package com.fourcamp.NutriPlan.dto.alimento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DispensaDto {
    private Integer idDispensa;
    private Integer quantidade;
    private String nomeAlimento;
    private Integer fkIdAlimento;

}

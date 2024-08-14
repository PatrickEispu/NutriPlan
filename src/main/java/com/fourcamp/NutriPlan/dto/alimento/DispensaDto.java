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
    Integer idDispensa;
    Integer quantidade;
    String nomeAlimento;
    Integer fkIdAlimento;

}

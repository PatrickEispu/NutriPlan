package com.fourcamp.NutriPlan.model.dispensa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DispensaEntity {
    Integer idDispensa;
    Integer quantidade;
    String nomeAlimento;
    Integer fkIdAlimento;
}

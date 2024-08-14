package com.fourcamp.NutriPlan.dto.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class MetaDto {
    Integer idMeta;
    Double pesoDesejado;
    String tempoMeta;
    Integer fkIdCliente;

}

package com.fourcamp.NutriPlan.dtos.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class MetaDto {

    private Integer idConta;
    private Double pesoDesejado;
    private String idTempo;
    private String idObjetivo;

}

package com.fourcamp.NutriPlan.model.meta;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)

public class MetaEntity {
    Integer idMeta;
    Double pesoDesejado;
    String tempoMeta;
    Integer fkIdCliente;
}

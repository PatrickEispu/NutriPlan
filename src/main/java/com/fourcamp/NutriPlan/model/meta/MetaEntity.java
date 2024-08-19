package com.fourcamp.NutriPlan.model.meta;


import com.fourcamp.NutriPlan.model.conta.ContaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)

public class MetaEntity extends ContaEntity {

    private Integer idConta;
    private Double pesoDesejado;
    private Integer idObjetivo;
    private Integer idTempo;
}

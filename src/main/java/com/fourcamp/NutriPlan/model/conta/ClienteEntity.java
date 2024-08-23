package com.fourcamp.NutriPlan.model.conta;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClienteEntity extends ContaEntity {
    private Integer fkNrIdConta;
    private String dsGenero;
    private Double nrPeso;
    private Double nrAltura;
    private String dsDataNascimento;
    private Double nrTmb;
    private Double nrGet;
    private String fkNrIdCategoria;
}
package com.fourcamp.NutriPlan.dto.alimento;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AlimentoDto {

    private Integer idAlimento;
    private String idCategoriaAlimento;
    private Double kcal;
    private Double carboidrato;
    private Double proteina;
    private Double gordura;
    private Double quantidade;
    private String nome;
}

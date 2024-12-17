package com.fourcamp.NutriPlan.model.alimento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoriaAlimentoEntity implements Serializable {

    private Integer idCategoriaAlimento;
    private String nomeCategoria;
}

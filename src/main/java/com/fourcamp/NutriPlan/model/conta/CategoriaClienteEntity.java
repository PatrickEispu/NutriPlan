package com.fourcamp.NutriPlan.model.conta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoriaClienteEntity implements Serializable {

    private Integer idCategoria;
    private String nomeCategoria;
}

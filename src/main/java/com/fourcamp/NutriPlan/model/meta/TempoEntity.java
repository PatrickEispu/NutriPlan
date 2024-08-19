package com.fourcamp.NutriPlan.model.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TempoEntity implements Serializable {

    private Integer idTempo;
    private String descricaoTempo;
}

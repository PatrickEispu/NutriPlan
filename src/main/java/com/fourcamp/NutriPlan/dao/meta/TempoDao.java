package com.fourcamp.NutriPlan.dao.meta;

import com.fourcamp.NutriPlan.model.meta.TempoEntity;

public interface TempoDao {

    TempoEntity buscarTempoPorNome(String nome);
}

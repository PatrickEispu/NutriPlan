package com.fourcamp.NutriPlan.dao.meta;

import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;

public interface ObjetivoDao {

    ObjetivoEntity buscarObjetivoPorNome(String nome);
}

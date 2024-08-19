package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;

public interface CategoriaAlimentoDao {
    CategoriaAlimentoEntity buscarCategoriaAlimentoPorNome(String nome);
}

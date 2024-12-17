package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.CategoriaClienteEntity;

public interface CategoriaClienteDao {

    CategoriaClienteEntity buscarCategoriaPorNome(String nome);
}

package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.TipoContaEntity;

public interface TipoContaDao {

    TipoContaEntity buscarTipoContaPorNome(String nome);
}

package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.RefeicaoEntity;

public interface RefeicaoDao {


    RefeicaoEntity adicionarAlimentoNaRefeicao(Integer idRefeicao, Integer idAlimento, Integer quantidade);

    Integer criarRefeicao(Integer idConta);
}

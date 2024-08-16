package com.fourcamp.NutriPlan.dao.alimento;

public interface RefeicaoDao {


    Integer adicionarAlimentoNaRefeicao(Integer idRefeicao, Integer idAlimento, Integer quantidade);

    Integer criarRefeicao(Integer idConta);
}

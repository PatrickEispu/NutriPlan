package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.model.alimento.RefeicaoEntity;

import java.util.List;

public interface RefeicaoDao {


    RefeicaoEntity adicionarAlimentoNaRefeicao(Integer idRefeicao, Integer idAlimento, Integer quantidade);

    Integer criarRefeicao(Integer idConta);

    List<RefeicaoEntity> getRefeicao(Integer fkIdRefeicao);

    List<AlimentoEntity> getRefeicaoAlimentoList(Integer fkNrIdRefeicao);
}

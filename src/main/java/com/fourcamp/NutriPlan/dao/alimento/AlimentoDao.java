package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;

import java.util.List;

public interface AlimentoDao {
    AlimentoEntity criarAlimento(AlimentoEntity alimento);

    List<AlimentoEntity> listarTodosAlimentos();

    AlimentoEntity buscarAlimentoPorNome(String nome);
}

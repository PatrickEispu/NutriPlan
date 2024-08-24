package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;

import java.util.List;

public interface AlimentoDao {
    AlimentoEntity criarAlimento(AlimentoEntity alimento);

    List<AlimentoEntity> listarTodosAlimentos();

    AlimentoEntity buscarAlimentoPorNome(String nome);

    boolean verificarAlimentoExistente(String nome);

    Integer getIdAlimentoPorNome(String nome);

    String getAlimentoNomePorId(Integer fkNrIdAlimento);
}

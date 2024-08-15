package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;

import java.util.List;

public interface AlimentoDao {
    int criarAlimento(int idCategoriaAlimento, double kcal, double carboidrato, double proteina, double gordura, double quantidade, String nome);

    List<AlimentoEntity> listarTodosAlimentos();

    AlimentoEntity buscarAlimentoPorNome(String nome);

    Integer getIdAlimentoPorNome(String nome);
}

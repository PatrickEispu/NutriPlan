package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.Alimento;

import java.util.List;

public interface AlimentoDao {
    void criarAlimento(Double kcal, Double carboidrato, Double proteina, Double gordura, Double quantidade, String nome);

    List<Alimento> listarAlimentos();

    Alimento buscarAlimentoPorNome(String nome);
}

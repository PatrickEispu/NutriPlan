package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.Alimento;

import java.util.List;

public interface AlimentoDao {
    int criarAlimento(int idCategoriaAlimento, double kcal, double carboidrato, double proteina, double gordura, double quantidade, String nome);

    List<Alimento> listarTodosAlimentos();

    Alimento buscarAlimentoPorNome(String nome);
}

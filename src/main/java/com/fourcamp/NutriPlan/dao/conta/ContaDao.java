package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.ContaEntity;

public interface ContaDao {
    boolean verificarEmailExistente(String email);

    ContaEntity criarConta(ContaEntity conta);

    ContaEntity buscarContaPorId(int idConta);

    Integer getIdContaPorEmail(String email);
}

package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.ContaEntity;

public interface ContaDao {
    ContaEntity getIdContaPorEmail(ContaEntity email);

    ContaEntity criarConta(ContaEntity conta);

    ContaEntity buscarContaPorId(int idConta);
}

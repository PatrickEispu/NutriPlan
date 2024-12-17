package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.dtos.alimento.DispensaDto;

import java.util.List;

public interface DispensaDao {
    void addAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer quantidade);

    Integer criarDispensa(Integer idConta);

    Integer dispensaExiste(Integer idConta);

    Integer getDispensa(Integer idConta);

    Integer getAlimentoDispensaCount(Integer idDispensa);

    Integer alimentoExisteNaDispensa(Integer idDispensa, Integer idAlimento);

    void atualizarAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer nrQuantidade);

    Integer getAlimentoQuantidade(Integer idDispensa, Integer idAlimento);

    List<DispensaDto> getAlimentoList(Integer idDispensa);
}

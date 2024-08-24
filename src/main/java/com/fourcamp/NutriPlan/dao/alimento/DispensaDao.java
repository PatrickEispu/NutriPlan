package com.fourcamp.NutriPlan.dao.alimento;

public interface DispensaDao {
    void addAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer quantidade);

    Integer criarDispensa(Integer idConta);

    Integer dispensaExiste(Integer idConta);

    Integer getDispensa(Integer idConta);

    Integer getAlimentoDispensaCount(Integer idDispensa);

    Integer alimentoExisteNaDispensa(Integer idDispensa, Integer idAlimento);

    void atualizarAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer nrQuantidade);

    Integer getALimentoQuantidade(Integer idDispensa, Integer idAlimento);
}

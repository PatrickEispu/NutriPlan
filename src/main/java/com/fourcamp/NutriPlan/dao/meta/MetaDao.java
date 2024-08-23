package com.fourcamp.NutriPlan.dao.meta;

import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;

public interface MetaDao {

    MetaEntity criarMeta (MetaEntity meta);

    void salvarMetaDiaria(Integer idConta, MacrosDto macroDiario);

    MetaEntity getMeta(Integer idConta);

    TempoEntity buscarTempoPorId(Integer idTempo);

    ObjetivoEntity buscarObjetivoPorId(Integer idObjetivo);
}

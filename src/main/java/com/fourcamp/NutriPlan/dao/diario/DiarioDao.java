package com.fourcamp.NutriPlan.dao.diario;

import com.fourcamp.NutriPlan.dtos.diario.DiarioDto;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;

import java.util.List;

public interface DiarioDao {
    DiarioEntity salvarDiario(DiarioDto diario);

    List<DiarioEntity> getDiarioList(Integer idConta);
}

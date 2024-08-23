package com.fourcamp.NutriPlan.dao.diario;

import com.fourcamp.NutriPlan.dtos.diario.DiarioDto;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;

public interface DiarioDao {
    DiarioEntity salvarDiario(DiarioDto diario);
}

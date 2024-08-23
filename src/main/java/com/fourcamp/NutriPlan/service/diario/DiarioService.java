package com.fourcamp.NutriPlan.service.diario;

import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
import com.fourcamp.NutriPlan.dao.diario.DiarioDao;
import com.fourcamp.NutriPlan.dtos.diario.DiarioDto;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DiarioService {


    @Autowired
    AlimentoDao alimentoDao;
    @Autowired
    DiarioDao diarioDao;


    public DiarioEntity salvarDiario(Integer idConta, Integer idRefeicao) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.now();
        String dataStr = data.format(dtf);

        DiarioDto diario = new DiarioDto();
        diario.setFkIdConta(idConta);
        diario.setData(dataStr);
        diario.setFkIdRefeicao(idRefeicao);

        return diarioDao.salvarDiario(diario);

    }
}

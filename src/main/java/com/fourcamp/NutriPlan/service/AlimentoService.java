package com.fourcamp.NutriPlan.service;

import com.fourcamp.NutriPlan.dao.JdbcTemplateDao;
import com.fourcamp.NutriPlan.dao.impl.JdbcTemplateDaoImpl;
import com.fourcamp.NutriPlan.exception.AlimentoNotFoundException;
import com.fourcamp.NutriPlan.exception.CriacaoAlimentoException;
import com.fourcamp.NutriPlan.model.Alimento;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private JdbcTemplateDao jdbcTemplateDao;

    public String criarAlimento(Double kcal, Double carboidrato, Double proteina, Double gordura, Double quantidade, String nome) {
        jdbcTemplateDao.criarAlimento(kcal, carboidrato, proteina, gordura, quantidade, nome);
        return Constantes.MSG_CRIACAO_ALIMENTO_SUCESSO;
    }

    public List<Alimento> visualizarAlimentos() {
        return jdbcTemplateDao.listarAlimentos();
    }
}

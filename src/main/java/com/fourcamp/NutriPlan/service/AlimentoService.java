package com.fourcamp.NutriPlan.service;

import com.fourcamp.NutriPlan.dao.ClienteDao;
import com.fourcamp.NutriPlan.dao.impl.AlimentoDao;
import com.fourcamp.NutriPlan.model.Alimento;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private AlimentoDao alimentoDao;


    public String criarAlimento(Double kcal, Double carboidrato, Double proteina, Double gordura, Double quantidade, String nome) {
        alimentoDao.criarAlimento(kcal, carboidrato, proteina, gordura, quantidade, nome);
        return Constantes.MSG_CRIACAO_ALIMENTO_SUCESSO;
    }

    public List<Alimento> visualizarAlimentos() {
        return alimentoDao.listarAlimentos();
    }
}

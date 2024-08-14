package com.fourcamp.NutriPlan.service.diario;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.dto.alimento.RefeicaoRequest;
import com.fourcamp.NutriPlan.exception.PlanoException;
import com.fourcamp.NutriPlan.model.alimento.Alimento;
import com.fourcamp.NutriPlan.model.diario.Diario;
import com.fourcamp.NutriPlan.utils.Arredondamento;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiarioService {

    @Autowired
    ClienteDao clienteDao;

    @Autowired
    AlimentoDao alimentoDao;




}

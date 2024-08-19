package com.fourcamp.NutriPlan.service.alimento;


import com.fourcamp.NutriPlan.dao.alimento.DispensaDao;
import com.fourcamp.NutriPlan.dto.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.dto.alimento.DispensaDto;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispensaService {
    @Autowired
    DispensaDao dispensaDao;
    @Autowired
    ContaService contaService;
    @Autowired
    AlimentoService alimentoService;

    public String addAlimentoNaDispensa(String email, List<DispensaDto> dispensaDtoList) {
        Integer idConta = contaService.getIdContaPorEmail(email);
        Integer idDispensa = dispensaDao.criarDispensa(idConta);


        for (DispensaDto dispensaDto : dispensaDtoList) {
            Integer idAlimento = alimentoService.getIdAlimentoPorNome(dispensaDto.getNomeAlimento());

            dispensaDao.addAlimentoNaDispensa(idDispensa, idAlimento, dispensaDto.getQuantidade());
        }
        return dispensaDtoList.toString();
    }
}

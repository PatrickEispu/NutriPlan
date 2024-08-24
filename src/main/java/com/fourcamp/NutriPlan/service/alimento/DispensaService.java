package com.fourcamp.NutriPlan.service.alimento;


import com.fourcamp.NutriPlan.dao.alimento.DispensaDao;
import com.fourcamp.NutriPlan.dtos.alimento.DispensaDto;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Integer idDispensa;
      if(!dispensaExiste(idConta))
      {
         idDispensa = dispensaDao.criarDispensa(idConta);
       }
      else
      {
          idDispensa = dispensaDao.getDispensa(idConta);
      }

        List<String> dispensaToString = new ArrayList<>();

        for (DispensaDto dispensaDto : dispensaDtoList) {
            Integer idAlimento = alimentoService.getIdAlimentoPorNome(dispensaDto.getNomeAlimento());
            dispensaDao.addAlimentoNaDispensa(idDispensa, idAlimento, dispensaDto.getNrQuantidade());

            dispensaToString.add("-"+dispensaDto.getNomeAlimento()+ "\n" + " Quantidade: "+dispensaDto.getNrQuantidade()+"\n");
        }
        return "alimentos adicionados na dispensa: \n"
                + dispensaToString;
    }

    private boolean dispensaExiste(Integer idConta) {
       Integer dispensaCount = dispensaDao.dispensaExiste(idConta);
        if (dispensaCount>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}

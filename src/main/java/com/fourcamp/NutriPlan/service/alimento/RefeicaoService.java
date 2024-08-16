package com.fourcamp.NutriPlan.service.alimento;


import com.fourcamp.NutriPlan.dao.alimento.RefeicaoDao;
import com.fourcamp.NutriPlan.dao.diario.DiarioDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.dto.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.dto.alimento.RefeicaoDto;
import com.fourcamp.NutriPlan.dto.alimento.RefeicaoRequest;
import com.fourcamp.NutriPlan.model.alimento.Alimento;
import com.fourcamp.NutriPlan.service.conta.ClienteService;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import com.fourcamp.NutriPlan.service.diario.DiarioService;
import com.fourcamp.NutriPlan.utils.Arredondamento;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RefeicaoService {

    @Autowired
    AlimentoService alimentoService;
    @Autowired
    ContaService contaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    RefeicaoDao refeicaoDao;
    @Autowired
    DiarioService diarioService;

    public String adicionarRefeicao(String email, List<AlimentoDto> alimentoDtoList)
    {
        MacrosDto macroTotal = new MacrosDto();
        for (AlimentoDto alimento: alimentoDtoList)
        {
            MacrosDto alimentoSalvo = alimentoService.consultarTabelaNutricional(alimento.getNome());
            Double kcal = macroTotal.getKcalTotais() + alimentoSalvo.getKcalTotais();
            Double carb = macroTotal.getCarboidrato() + alimentoSalvo.getCarboidrato();
            Double prot = macroTotal.getProteina() + alimentoSalvo.getProteina();
            Double gord = macroTotal.getGordura() + alimentoSalvo.getGordura();

            macroTotal.setKcalTotais(kcal);
            macroTotal.setCarboidrato(carb);
            macroTotal.setProteina(prot);
            macroTotal.setGordura(gord);

           //TODO criar funcao para pegar o id da conta e continuar a criacao da refeicao.
            Integer idConta = contaService.getIdContaPorEmail(email);
            Integer idAlimento = alimentoService.getIdAlimentoPorNome(alimento.getNome());

            

            //Integer idConta = conta
            refeicaoDao.salvarRefeicao(idConta, idAlimento);
        }

    }

//    public String adicionarRefeicao(String email, RefeicaoRequest refeicaoRequest) {
//        MacrosDto tabelaAlimento = alimentoService.consultarTabelaNutricional(refeicaoRequest.getAlimento());
//        MacrosDto tabelaAlimentoCalculado = alimentoService.calcularQuantidadeAlimento(tabelaAlimento, refeicaoRequest.getQuantidade());
//
//        MacrosDto planoAtual = new MacrosDto(
//                tabelaAlimentoCalculado.getKcalTotais(),
//                tabelaAlimentoCalculado.getCarboidrato(),
//                tabelaAlimentoCalculado.getProteina(),
//                tabelaAlimentoCalculado.getGordura()
//        );
//
//        MacrosDto planoAposAdicao = alimentoService.consultarPlanoCliente(email, planoAtual);
//
//        refeicaoDao.salvarDiario(
//                email,
//                refeicaoRequest.getAlimento(),
//                refeicaoRequest.getQuantidade(),
//                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getKcalTotais()),
//                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getCarboidrato()),
//                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getProteina()),
//                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getGordura()),
//                new Date()
//        );
//
//        return Constantes.MSG_ATUALIZACAO_PLANO;
//    }
//

}

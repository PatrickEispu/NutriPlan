package com.fourcamp.NutriPlan.service.alimento;


import com.fourcamp.NutriPlan.dao.alimento.RefeicaoDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.dto.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.service.conta.ClienteService;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import com.fourcamp.NutriPlan.service.diario.DiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String adicionarRefeicao(String email, List<AlimentoDto> alimentoDtoList) {
        MacrosDto macroTotal = new MacrosDto();
        Integer idConta = contaService.getIdContaPorEmail(email);
        Integer idRefeicao = refeicaoDao.criarRefeicao(idConta);

        for (AlimentoDto alimento : alimentoDtoList) {
            MacrosDto alimentoSalvo = alimentoService.consultarTabelaNutricional(alimento.getNome());
            MacrosDto alimentoCalculado = alimentoService.calcularQuantidadeAlimento(alimentoSalvo, alimento.getQuantidade());

            Double kcal = macroTotal.getKcalTotais() + alimentoCalculado.getKcalTotais();
            Double carb = macroTotal.getCarboidrato() + alimentoCalculado.getCarboidrato();
            Double prot = macroTotal.getProteina() + alimentoCalculado.getProteina();
            Double gord = macroTotal.getGordura() + alimentoCalculado.getGordura();

            macroTotal.setKcalTotais(kcal);
            macroTotal.setCarboidrato(carb);
            macroTotal.setProteina(prot);
            macroTotal.setGordura(gord);

            Integer idAlimento = alimentoService.getIdAlimentoPorNome(alimento.getNome());
            Integer quantidade = alimento.getQuantidade().intValue();

            refeicaoDao.adicionarAlimentoNaRefeicao(idRefeicao, idAlimento, quantidade);
            diarioService.salvarDiario(idConta,idRefeicao);

        }
        return alimentoDtoList.toString();
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

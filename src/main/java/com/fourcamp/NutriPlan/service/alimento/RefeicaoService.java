package com.fourcamp.NutriPlan.service.alimento;


import com.fourcamp.NutriPlan.dao.alimento.RefeicaoDao;
import com.fourcamp.NutriPlan.dao.diario.DiarioDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.dto.alimento.RefeicaoRequest;
import com.fourcamp.NutriPlan.service.conta.ClienteService;
import com.fourcamp.NutriPlan.service.diario.DiarioService;
import com.fourcamp.NutriPlan.utils.Arredondamento;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefeicaoService {

    @Autowired
    AlimentoService alimentoService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    RefeicaoDao refeicaoDao;
    @Autowired
    DiarioService diarioService;

    public String adicionarRefeicao(String email, RefeicaoRequest refeicaoRequest) {
        MacrosDto tabelaAlimento = alimentoService.consultarTabelaNutricional(refeicaoRequest.getAlimento());
        MacrosDto tabelaAlimentoCalculado = alimentoService.calcularQuantidadeAlimento(tabelaAlimento, refeicaoRequest.getQuantidade());

        MacrosDto planoAtual = new MacrosDto(
                tabelaAlimentoCalculado.getKcalTotais(),
                tabelaAlimentoCalculado.getCarboidrato(),
                tabelaAlimentoCalculado.getProteina(),
                tabelaAlimentoCalculado.getGordura()
        );

        MacrosDto planoAposAdicao = alimentoService.consultarPlanoCliente(email, planoAtual);

        refeicaoDao.salvarDiario(
                email,
                refeicaoRequest.getAlimento(),
                refeicaoRequest.getQuantidade(),
                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getKcalTotais()),
                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getCarboidrato()),
                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getProteina()),
                Arredondamento.roundToThreeDecimalPlaces(planoAposAdicao.getGordura()),
                new Date()
        );

        return Constantes.MSG_ATUALIZACAO_PLANO;
    }
}

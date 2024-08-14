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

    public String adicionarRefeicao(String email, RefeicaoRequest refeicaoRequest) {
        MacrosDto tabelaAlimento = consultarTabelaNutricional(refeicaoRequest.getAlimento());
        MacrosDto tabelaAlimentoCalculado = calcularQuantidadeAlimento(tabelaAlimento, refeicaoRequest.getQuantidade());

        MacrosDto planoAtual = new MacrosDto(
                tabelaAlimentoCalculado.getKcalTotais(),
                tabelaAlimentoCalculado.getCarboidrato(),
                tabelaAlimentoCalculado.getProteina(),
                tabelaAlimentoCalculado.getGordura()
        );

        MacrosDto planoAposAdicao = consultarPlanoCliente(email, planoAtual);

        clienteDao.salvarDiario(
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

    public MacrosDto consultarTabelaNutricional(String nomeAlimento){
        Alimento alimento = alimentoDao.buscarAlimentoPorNome(nomeAlimento);

        return new MacrosDto(
                alimento.getKcal(),
                alimento.getCarboidrato(),
                alimento.getProteina(),
                alimento.getGordura()
        );
    }

    public MacrosDto calcularQuantidadeAlimento(MacrosDto macro, Double quantidade) {
        macro.setKcalTotais((quantidade / 100) * macro.getKcalTotais());
        macro.setCarboidrato((quantidade / 100) * macro.getCarboidrato());
        macro.setProteina((quantidade / 100) * macro.getProteina());
        macro.setGordura((quantidade / 100) * macro.getGordura());
        return macro;
    }

    public MacrosDto consultarPlanoCliente(String email, MacrosDto planoAtual) {
        List<Diario> diarios = clienteDao.buscarPlanoCliente(email);

        if (!diarios.isEmpty()) {
            Diario diario = diarios.get(0);
            planoAtual.setKcalTotais(diario.getKcal() - planoAtual.getKcalTotais());
            planoAtual.setCarboidrato(diario.getCarboidrato() - planoAtual.getCarboidrato());
            planoAtual.setProteina(diario.getProteina() - planoAtual.getProteina());
            planoAtual.setGordura(diario.getGordura() - planoAtual.getGordura());
        } else {
            throw new PlanoException();
        }

        return planoAtual;
    }
}

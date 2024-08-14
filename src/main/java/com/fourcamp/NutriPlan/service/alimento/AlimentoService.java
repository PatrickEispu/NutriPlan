package com.fourcamp.NutriPlan.service.alimento;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.exception.PlanoException;
import com.fourcamp.NutriPlan.model.alimento.Alimento;
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

    private double calcularProteina(double peso) {
        return 2.0 * peso;
    }

    private double calcularCarboidratos(double gastoEnergetico, double proteinas, double gorduras) {
        double proteinasEmCalorias = proteinas * 4;
        double gordurasEmCalorias = gorduras * 9;
        return (gastoEnergetico - proteinasEmCalorias - gordurasEmCalorias) / 4;
    }

    public MacrosDto calcularQuantidadeAlimento(MacrosDto macro, Double quantidade) {
        macro.setKcalTotais((quantidade / 100) * macro.getKcalTotais());
        macro.setCarboidrato((quantidade / 100) * macro.getCarboidrato());
        macro.setProteina((quantidade / 100) * macro.getProteina());
        macro.setGordura((quantidade / 100) * macro.getGordura());
        return macro;
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

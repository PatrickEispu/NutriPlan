package com.fourcamp.NutriPlan.service.alimento;

import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
import com.fourcamp.NutriPlan.dao.alimento.CategoriaAlimentoDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoDao alimentoDao;

    @Autowired
    private CategoriaAlimentoDao categoriaAlimentoDao;

    public String criarAlimento(String nomeCategoria, Double kcal, Double carboidrato, Double proteina, Double gordura, Double quantidade, String nome) {
        // Buscar a categoria de alimento pelo nome
        CategoriaAlimentoEntity categoriaAlimento = categoriaAlimentoDao.buscarCategoriaAlimentoPorNome(nomeCategoria);

        if (categoriaAlimento == null) {
            throw new IllegalArgumentException("Categoria de alimento não encontrada: " + nomeCategoria);
        }

        // Criar o alimento usando o ID da categoria encontrada
        alimentoDao.criarAlimento(categoriaAlimento.getIdCategoriaAlimento(), kcal, carboidrato, proteina, gordura, quantidade, nome);
        return Constantes.MSG_CRIACAO_ALIMENTO_SUCESSO;
    }
    public List<AlimentoEntity> visualizarAlimentos() {
        return alimentoDao.listarTodosAlimentos();
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
        AlimentoEntity alimentoEntity = alimentoDao.buscarAlimentoPorNome(nomeAlimento);

        return new MacrosDto(
                alimentoEntity.getKcal(),
                alimentoEntity.getCarboidrato(),
                alimentoEntity.getProteina(),
                alimentoEntity.getGordura()
        );
    }

    /*public MacrosDto consultarPlanoCliente(String email, MacrosDto planoAtual) {
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
    }*/


}

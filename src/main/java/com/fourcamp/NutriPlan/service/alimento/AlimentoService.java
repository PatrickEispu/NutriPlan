package com.fourcamp.NutriPlan.service.alimento;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
import com.fourcamp.NutriPlan.dao.alimento.CategoriaAlimentoDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.dto.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.enuns.CategoriaAlimentoEnum;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private AlimentoDao alimentoDao;

    @Autowired
    private CategoriaAlimentoDao categoriaAlimentoDao;

    public AlimentoDto criarAlimento(AlimentoDto alimentoDto) {
        // Buscar a categoria de alimento pelo nome
        CategoriaAlimentoEntity categoriaAlimento = categoriaAlimentoDao.buscarCategoriaAlimentoPorNome(alimentoDto.getIdCategoriaAlimento());

        if (categoriaAlimento == null) {
            throw new IllegalArgumentException("Categoria de alimento não encontrada: " + categoriaAlimento);
        }

        AlimentoEntity alimento = AlimentoEntity.builder()
                .idCategoriaAlimento(categoriaAlimento.getIdCategoriaAlimento())
                .kcal(alimentoDto.getKcal())
                .carboidrato(alimentoDto.getCarboidrato())
                .proteina(alimentoDto.getProteina())
                .gordura(alimentoDto.getGordura())
                .quantidade(alimentoDto.getQuantidade())
                .nome(alimentoDto.getNome())
                .build();
        // Criar o alimento usando o ID da categoria encontrada
       AlimentoEntity alimentoSalvo = alimentoDao.criarAlimento(alimento);
       return mapearAlimento(categoriaAlimento, alimentoSalvo);
    }

    private AlimentoDto mapearAlimento(CategoriaAlimentoEntity categoriaAlimento, AlimentoEntity alimentoSalvo){
        return AlimentoDto.builder()
                .idCategoriaAlimento(String.valueOf(CategoriaAlimentoEnum.valueOf(categoriaAlimento.getNomeCategoria())))
                .kcal(alimentoSalvo.getKcal())
                .carboidrato(alimentoSalvo.getCarboidrato())
                .proteina(alimentoSalvo.getProteina())
                .gordura(alimentoSalvo.getGordura())
                .quantidade(alimentoSalvo.getQuantidade())
                .nome(alimentoSalvo.getNome())
                .build();
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

    //diario
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


    public Integer getIdAlimentoPorNome(String nome) {
        return alimentoDao.getIdAlimentoPorNome(nome);
    }
}

package com.fourcamp.NutriPlan.service.alimento;

import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
import com.fourcamp.NutriPlan.dao.alimento.CategoriaAlimentoDao;
import com.fourcamp.NutriPlan.dtos.MacrosDto;
import com.fourcamp.NutriPlan.dtos.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.enuns.CategoriaAlimentoEnum;
import com.fourcamp.NutriPlan.exception.CategoriaAlimentoException;
import com.fourcamp.NutriPlan.exception.CriacaoAlimentoException;
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

    public AlimentoDto criarAlimento(AlimentoDto alimentoDto) {

            // Buscar a categoria de alimento pelo nome
            CategoriaAlimentoEntity categoriaAlimento = categoriaAlimentoDao.buscarCategoriaAlimentoPorNome(alimentoDto.getCategoriaAlimento());

            if (categoriaAlimento == null) {
                throw new CategoriaAlimentoException(Constantes.MSG_CATEGORIA_ALIMENTO_INVALIDO + categoriaAlimento);
            }

            // Verificar se o alimento já existe na base de dados
            Boolean alimentoExiste = alimentoDao.verificarAlimentoExistente(alimentoDto.getNome());
            if (alimentoExiste != null && alimentoExiste) {
                throw new CriacaoAlimentoException(Constantes.MSG_NOME_ALIMENTO_JA_CADASTRADO);
            }

        try {
            AlimentoEntity alimento = AlimentoEntity.builder()
                    .CategoriaAlimento(categoriaAlimento.getNomeCategoria())
                    .kcal(alimentoDto.getKcal())
                    .carboidrato(alimentoDto.getCarboidrato())
                    .proteina(alimentoDto.getProteina())
                    .gordura(alimentoDto.getGordura())
                    .quantidade(alimentoDto.getQuantidade())
                    .nome(alimentoDto.getNome())
                    .build();

            AlimentoEntity alimentoSalvo = alimentoDao.criarAlimento(alimento);
            return mapearAlimento(categoriaAlimento, alimentoSalvo);
        } catch (Exception e) {
            throw new CriacaoAlimentoException(Constantes.MSG_ERRO_CRIACAO_ALIMENTO + e.getMessage());
        }
    }

    private AlimentoDto mapearAlimento(CategoriaAlimentoEntity categoriaAlimento, AlimentoEntity alimentoSalvo){
        return AlimentoDto.builder()
                .CategoriaAlimento(String.valueOf(CategoriaAlimentoEnum.valueOf(categoriaAlimento.getNomeCategoria())))
                .kcal(alimentoSalvo.getKcal())
                .carboidrato(alimentoSalvo.getCarboidrato())
                .proteina(alimentoSalvo.getProteina())
                .gordura(alimentoSalvo.getGordura())
                .quantidade(alimentoSalvo.getQuantidade())
                .nome(alimentoSalvo.getNome())
                .build();
    }
    public List<AlimentoEntity> visualizarAlimentos() {
        try {
            return alimentoDao.listarTodosAlimentos();
        } catch (Exception e){
            throw new CriacaoAlimentoException(Constantes.MSG_ERRO_LISTAR_ALIMENTO + e.getMessage());
        }
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
                alimentoEntity.getGordura(),
                alimentoEntity.getIdAlimento()
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

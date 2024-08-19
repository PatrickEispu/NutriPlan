package com.fourcamp.NutriPlan.service.meta;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.meta.MetaDao;
import com.fourcamp.NutriPlan.dao.meta.ObjetivoDao;
import com.fourcamp.NutriPlan.dao.meta.TempoDao;
import com.fourcamp.NutriPlan.dto.meta.MetaDto;
import com.fourcamp.NutriPlan.enuns.ObjetivoEnum;
import com.fourcamp.NutriPlan.enuns.TempoEnum;
import com.fourcamp.NutriPlan.enuns.TipoContaEnum;
import com.fourcamp.NutriPlan.exception.MetaException;
import com.fourcamp.NutriPlan.exception.ObjetivoException;
import com.fourcamp.NutriPlan.exception.TempoException;
import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;
import com.fourcamp.NutriPlan.utils.CalculoIdade;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private CalculoIdade calculoIdade;

    @Autowired
    private MetaDao metaDao;

    @Autowired
    private TempoDao tempoDao;
    @Autowired
    private ObjetivoDao objetivoDao;

    public MetaDto criarMeta (MetaDto metaDto) {

        //Buscar tempo pelo nome
        TempoEntity tempo = tempoDao.buscarObjetivoPorNome(metaDto.getIdTempo());

        if (tempo == null) {
            throw new TempoException(Constantes.MSG_TEMPO_META_INVALIDO + tempo);
        }

        //Buscar objetivo pelo nome
        ObjetivoEntity objetivo = objetivoDao.buscarObjetivoPorNome(metaDto.getIdObjetivo());

        if (objetivo == null) {
            throw new ObjetivoException(Constantes.MSG_OBJETIVO_META_INVALIDO + objetivo);
        }

        try {
            MetaEntity meta = MetaEntity.builder()
                    .pesoDesejado(metaDto.getPesoDesejado())
                    .idConta(metaDto.getIdConta())
                    .idTempo(tempo.getIdTempo())
                    .idObjetivo(objetivo.getIdObjetivo())
                    .build();

            MetaEntity metaSalva = metaDao.criarMeta(meta);
            return mapearMeta(tempo, objetivo, metaSalva);
        } catch (Exception e) {
            throw new MetaException(Constantes.MSG_ERRO_META);
        }
    }

        private MetaDto mapearMeta(TempoEntity tempo, ObjetivoEntity objetivo, MetaEntity metaSalva){
            return MetaDto.builder()
                    .pesoDesejado(metaSalva.getPesoDesejado())
                    .idConta(metaSalva.getIdConta())
                    .idTempo(String.valueOf(TempoEnum.valueOf(tempo.getDescricaoTempo())))
                    .idObjetivo(String.valueOf(ObjetivoEnum.valueOf(objetivo.getDescricaoObjetivo())))
                    .build();
        }


        //    public String acessarPlano(String email, String categoria, String tempoMeta, String genero, double peso, double altura, Date dataNascimento, String categoriaAtividade) {
//        double gastoEnergetico = calcularGETSalvar(email, categoria, tempoMeta, genero, peso, altura, dataNascimento, categoriaAtividade);
//
//        String categoriaUpper = categoria.trim().toUpperCase(Locale.ROOT);
//        String tempoCategoriaUpper = tempoMeta.trim().toUpperCase(Locale.ROOT);
//
//        double proteinas = 0;
//        double carboidratos = 0;
//        double gorduras = peso;
//
//        switch (categoriaUpper) {
//            case "PERDER PESO":
//                switch (tempoCategoriaUpper) {
//                    case "RAPIDO":
//                        proteinas = 2.5 * peso;
//                        break;
//                    case "MEDIO":
//                        proteinas = 2.2 * peso;
//                        break;
//                    case "LONGO PRAZO":
//                        proteinas = 2.0 * peso;
//                        break;
//                    default:
//                        throw new TempoMetaException();
//                }
//                break;
//            case "MANUTENCAO":
//            case "HIPERTROFIA":
//                proteinas = calcularProteina(peso);
//                break;
//            default:
//                throw new CategoriaException();
//        }
//
//        carboidratos = calcularCarboidratos(gastoEnergetico, proteinas, gorduras);
//
//        String planoNutricional = "Seu plano nutricional: \n" +
//                "Calorias necessárias: " + gastoEnergetico + " kcal\n" +
//                "Proteínas: " + proteinas + " g\n" +
//                "Carboidratos: " + carboidratos + " g\n" +
//                "Gorduras: " + gorduras + " g";
//
//        clienteDao.salvarDiario(
//                email,
//                "Plano Nutricional",
//                1,
//                Arredondamento.roundToThreeDecimalPlaces(gastoEnergetico),
//                Arredondamento.roundToThreeDecimalPlaces(carboidratos),
//                Arredondamento.roundToThreeDecimalPlaces(proteinas),
//                Arredondamento.roundToThreeDecimalPlaces(gorduras),
//                new Date());
//        return planoNutricional;
//   }
}
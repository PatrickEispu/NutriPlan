package com.fourcamp.NutriPlan.service.meta;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.meta.MetaDao;
import com.fourcamp.NutriPlan.dao.meta.ObjetivoDao;
import com.fourcamp.NutriPlan.dao.meta.TempoDao;
import com.fourcamp.NutriPlan.dtos.diario.MacrosDto;
import com.fourcamp.NutriPlan.dtos.meta.MetaDto;
import com.fourcamp.NutriPlan.dto.conta.ClientePrimeiroAcessoDto;
import com.fourcamp.NutriPlan.enuns.ObjetivoEnum;
import com.fourcamp.NutriPlan.enuns.TempoEnum;
import com.fourcamp.NutriPlan.exception.MetaException;
import com.fourcamp.NutriPlan.exception.ObjetivoException;
import com.fourcamp.NutriPlan.exception.TempoException;
import com.fourcamp.NutriPlan.model.alimento.RefeicaoEntity;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;
import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;
import com.fourcamp.NutriPlan.service.alimento.AlimentoService;
import com.fourcamp.NutriPlan.service.alimento.RefeicaoService;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import com.fourcamp.NutriPlan.service.diario.DiarioService;
import com.fourcamp.NutriPlan.utils.Arredondamento;
import com.fourcamp.NutriPlan.utils.CalculoIdade;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetaService {

    @Autowired
    ContaService contaService;
    @Autowired
    AlimentoService alimentoService;
    @Autowired
    RefeicaoService refeicaoService;

    @Autowired
    DiarioService diarioService;
    @Autowired
    public MetaDao metaDao;

    @Autowired
    public TempoDao tempoDao;
    @Autowired
    public ObjetivoDao objetivoDao;

    public MetaDto criarMeta(Integer idConta, MetaDto metaDto) {

        //Buscar tempo pelo nome
        TempoEntity tempo = getTempo(metaDto);
        //Buscar objetivo pelo nome
        ObjetivoEntity objetivo = getObjetivo(metaDto);
        //verificar se os dados passados são válidos
        verificaMetaRequest(tempo, objetivo);

        try {
            MetaEntity meta = MetaEntity.builder()
                    .pesoDesejado(metaDto.getPesoDesejado())
                    .idConta(idConta)
                    .idTempo(tempo.getIdTempo())
                    .idObjetivo(objetivo.getNrIdObjetivo())
                    .build();

            //salvar meta criada
            MetaEntity metaSalva = metaDao.criarMeta(meta);


            return mapearMeta(tempo, objetivo, metaSalva);
        } catch (Exception e) {
            throw new MetaException(Constantes.MSG_ERRO_META);
        }


    }

    public void verificaMetaRequest(TempoEntity tempo, ObjetivoEntity objetivo) {
        if (tempo == null) {
            throw new TempoException(Constantes.MSG_TEMPO_META_INVALIDO);
        }
        if (objetivo == null) {
            throw new ObjetivoException(Constantes.MSG_OBJETIVO_META_INVALIDO);
        }
    }

    public ObjetivoEntity getObjetivo(MetaDto metaDto) {
        return objetivoDao.buscarObjetivoPorNome(metaDto.getIdObjetivo());
    }

    public ObjetivoEntity getObjetivo(String objetivo) {
        return objetivoDao.buscarObjetivoPorNome(objetivo);
    }


    public TempoEntity getTempo(MetaDto metaDto) {
        return tempoDao.buscarTempoPorNome(metaDto.getIdTempo());
    }

    public TempoEntity getTempo(String tempo) {
        return tempoDao.buscarTempoPorNome(tempo);
    }

    public MetaDto mapearMeta(TempoEntity tempo, ObjetivoEntity objetivo, MetaEntity metaSalva) {
        return MetaDto.builder()
                .pesoDesejado(metaSalva.getPesoDesejado())
                .idConta(metaSalva.getIdConta())
                .idTempo(String.valueOf(TempoEnum.valueOf(tempo.getDescricaoTempo())))
                .idObjetivo(String.valueOf(ObjetivoEnum.valueOf(objetivo.getDsDescricaoObjetivo())))
                .build();
    }


    public String atualizarMetaDiaria(ClienteEntity cliente, String email, double gastoEnergetico, MetaDto metaSalva) {


        TempoEntity tempo = getTempo(metaSalva.getIdTempo());
        ObjetivoEntity objetivo = getObjetivo(metaSalva.getIdObjetivo());

        ObjetivoEnum objetivoEnum = ObjetivoEnum.valueOf(objetivo.getDsDescricaoObjetivo());
        TempoEnum tempoEnum = TempoEnum.valueOf(tempo.getDescricaoTempo());


        //Calcular proteina diaria
        double proteinas = getProteinas(cliente.getNrPeso(), objetivoEnum, tempoEnum);
        //calcular gordura diaria
        double gorduras = cliente.getNrPeso();
        //calcular carboidrato diario
        double carboidratos = alimentoService.calcularCarboidratos(gastoEnergetico, proteinas, gorduras);

        Arredondamento.roundToThreeDecimalPlaces(gastoEnergetico);
        Arredondamento.roundToThreeDecimalPlaces(carboidratos);
        Arredondamento.roundToThreeDecimalPlaces(proteinas);
        Arredondamento.roundToThreeDecimalPlaces(gorduras);


        MacrosDto macroDiario = new MacrosDto();
        macroDiario.setKcalTotais(gastoEnergetico);
        macroDiario.setProteina(proteinas);
        macroDiario.setCarboidrato(carboidratos);
        macroDiario.setGordura(gorduras);

        Integer idConta = contaService.getIdContaPorEmail(email);
        if (!metaDiariaExiste(idConta)) {
            metaDao.salvarMetaDiaria(idConta, macroDiario);
        } else {
            metaDao.atualizarMetaDiaria(idConta, macroDiario);
        }
        return "Seu plano nutricional: \n" +
                "Calorias necessárias: " + gastoEnergetico + " kcal\n" +
                "Proteínas: " + proteinas + " g\n" +
                "Carboidratos: " + carboidratos + " g\n" +
                "Gorduras: " + gorduras + " g";


    }

    private boolean metaDiariaExiste(Integer idConta) {
        Integer metaDiariaCount = metaDao.metaDiariaExiste(idConta);
        if (metaDiariaCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    //antigo acessar plano
    public String criarMetaDiaria(ClientePrimeiroAcessoDto cliente, String email, double gastoEnergetico) {

        TempoEntity tempo = getTempo(cliente.getTempo());
        ObjetivoEntity objetivo = getObjetivo(cliente.getObjetivo());


        ObjetivoEnum objetivoEnum = ObjetivoEnum.valueOf(objetivo.getDsDescricaoObjetivo());
        TempoEnum tempoEnum = TempoEnum.valueOf(tempo.getDescricaoTempo());

        //Calcular proteina diaria
        double proteinas = getProteinas(cliente.getPeso(), objetivoEnum, tempoEnum);
        //calcular gordura diaria
        double gorduras = cliente.getPeso();
        //calcular carboidrato diario
        double carboidratos = alimentoService.calcularCarboidratos(gastoEnergetico, proteinas, gorduras);

        Arredondamento.roundToThreeDecimalPlaces(gastoEnergetico);
        Arredondamento.roundToThreeDecimalPlaces(carboidratos);
        Arredondamento.roundToThreeDecimalPlaces(proteinas);
        Arredondamento.roundToThreeDecimalPlaces(gorduras);


        MacrosDto macroDiario = new MacrosDto();
        macroDiario.setKcalTotais(gastoEnergetico);
        macroDiario.setProteina(proteinas);
        macroDiario.setCarboidrato(carboidratos);
        macroDiario.setGordura(gorduras);

        Integer idConta = contaService.getIdContaPorEmail(email);
        metaDao.salvarMetaDiaria(idConta, macroDiario);


        return "Seu plano nutricional: \n" +
                "Calorias necessárias: " + gastoEnergetico + " kcal\n" +
                "Proteínas: " + proteinas + " g\n" +
                "Carboidratos: " + carboidratos + " g\n" +
                "Gorduras: " + gorduras + " g";


    }


    public double getProteinas(Double peso, ObjetivoEnum objetivoEnum, TempoEnum tempoEnum) {
        double proteinas;
        switch (objetivoEnum) {
            case PERDER_PESO:
                switch (tempoEnum) {
                    case RAPIDO:
                        proteinas = 2.5 * peso;
                        break;
                    case MEDIO:
                        proteinas = 2.2 * peso;
                        break;
                    case LONGO_PRAZO:
                        proteinas = 2.0 * peso;
                        break;
                    default:
                        throw new TempoException();
                }
                break;

            case MANUTENCAO:
                proteinas = 2.2 * peso;
                break;
            case HIPERTROFIA:
                proteinas = alimentoService.calcularProteina(peso);
                break;
            default:
                throw new ObjetivoException();
        }
        return proteinas;
    }


    public boolean metaExiste(Integer idConta) {
        Integer metaCount = metaDao.metaExiste(idConta);
        if (metaCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String acessarMeta(String email) {
        Integer idConta = contaService.getIdContaPorEmail(email);
        MacrosDto metaDiaria = metaDao.getMetaDiaria(idConta);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = LocalDate.now().format(dtf);

        List<DiarioEntity> diarioEntityList = diarioService.getDiarioList(idConta);
        List<DiarioEntity> diarioDoDia = new ArrayList<>();
        MacrosDto macroTotal = new MacrosDto();
        macroTotal.setKcalTotais(0.0);
        macroTotal.setCarboidrato(0.0);
        macroTotal.setProteina(0.0);
        macroTotal.setGordura(0.0);


        for (DiarioEntity diario : diarioEntityList) {
            if (diario.getData().equals(data)) {
                diarioDoDia.add(diario);
            }
        }

        for (DiarioEntity diario : diarioDoDia)
        {
            Integer loop = 0;
            for (RefeicaoEntity refeicao : diario.getRefeicao()) {
                if (loop>0)
                {
                    break;
                }

                MacrosDto refeicaoMacro = refeicaoService.refeicaoMacroTotal(refeicao);

                Double kcal = macroTotal.getKcalTotais() + refeicaoMacro.getKcalTotais();
                Double carb = macroTotal.getCarboidrato() + refeicaoMacro.getCarboidrato();
                Double prot = macroTotal.getProteina() + refeicaoMacro.getProteina();
                Double gord = macroTotal.getGordura() + refeicaoMacro.getGordura();

                kcal = Math.round(kcal * 100.0) / 100.0;
                carb = Math.round(carb * 100.0) / 100.0;
                prot = Math.round(prot * 100.0) / 100.0;
                gord = Math.round(gord * 100.0) / 100.0;

                macroTotal.setKcalTotais(kcal);
                macroTotal.setCarboidrato(carb);
                macroTotal.setProteina(prot);
                macroTotal.setGordura(gord);
                loop++;
            }

        }




        String metaDiariaStr = "Sua Meta Diaria é: \n" +
                "Calorias necessárias: " + metaDiaria.getKcalTotais() + " kcal\n" +
                "Proteínas: " + metaDiaria.getProteina() + " g\n" +
                "Carboidratos: " + metaDiaria.getCarboidrato() + " g\n" +
                "Gorduras: " + metaDiaria.getGordura() + " g";

        String macroTotalStr = "Você consumiu hoje: \n" +
                "Calorias: " + macroTotal.getKcalTotais() + " kcal\n" +
                "Proteínas: " + macroTotal.getProteina() + " g\n" +
                "Carboidratos: " + macroTotal.getCarboidrato() + " g\n" +
                "Gorduras: " + macroTotal.getGordura() + " g";

        return metaDiariaStr +
                "\n" +
                "\n" +
                macroTotalStr;
    }
}
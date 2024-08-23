package com.fourcamp.NutriPlan.service.meta;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.meta.MetaDao;
import com.fourcamp.NutriPlan.dao.meta.ObjetivoDao;
import com.fourcamp.NutriPlan.dao.meta.TempoDao;
import com.fourcamp.NutriPlan.dtos.meta.MetaDto;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.dto.conta.ClientePrimeiroAcessoDto;
import com.fourcamp.NutriPlan.dto.meta.MetaDto;
import com.fourcamp.NutriPlan.enuns.ObjetivoEnum;
import com.fourcamp.NutriPlan.enuns.TempoEnum;
import com.fourcamp.NutriPlan.exception.MetaException;
import com.fourcamp.NutriPlan.exception.ObjetivoException;
import com.fourcamp.NutriPlan.exception.TempoException;
import com.fourcamp.NutriPlan.model.conta.Cliente;
import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;
import com.fourcamp.NutriPlan.service.alimento.AlimentoService;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import com.fourcamp.NutriPlan.utils.Arredondamento;
import com.fourcamp.NutriPlan.utils.CalculoIdade;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

    @Autowired
    ContaService contaService;
//    @Autowired
   // ClienteService clienteService;
    @Autowired
    AlimentoService alimentoService;
    @Autowired
    public ClienteDao clienteDao;

    @Autowired
    public CalculoIdade calculoIdade;

    @Autowired
    public MetaDao metaDao;

    @Autowired
    public TempoDao tempoDao;
    @Autowired
    public ObjetivoDao objetivoDao;

    public MetaDto criarMeta(String email, MetaDto metaDto) {

        //Buscar tempo pelo nome
        TempoEntity tempo = getTempo(metaDto);
        //Buscar objetivo pelo nome
        ObjetivoEntity objetivo = getObjetivo(metaDto);
        //verificar se os dados passados são válidos
        verificaMetaRequest(tempo, objetivo);

        try {
            MetaEntity meta = MetaEntity.builder()
                    .pesoDesejado(metaDto.getPesoDesejado())
                    .idConta(metaDto.getIdConta())
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


    public void atualizarMetaDiaria(Cliente cliente,String email, double gastoEnergetico,MetaDto metaSalva) {

     //   Cliente cliente = clienteService.buscarClientePorId(metaSalva.getIdConta());

        TempoEntity tempo = getTempo(metaSalva.getIdTempo());
        ObjetivoEntity objetivo = getObjetivo(metaSalva.getIdObjetivo());

//        TempoEntity tempo = metaDao.buscarTempoPorId(metaSalva.getIdTempo());
//        ObjetivoEntity objetivo = metaDao.buscarObjetivoPorId(metaSalva.getIdObjetivo());


        ObjetivoEnum objetivoEnum = ObjetivoEnum.valueOf(objetivo.getDsDescricaoObjetivo());
        TempoEnum tempoEnum = TempoEnum.valueOf(tempo.getDescricaoTempo());


        //descobrir o GET do usuario
      //  double gastoEnergetico = clienteService.calcularGETSalvar(email);
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


    public MetaEntity getMeta(Integer idConta) {
        return metaDao.getMeta(idConta);
    }

    public ObjetivoEntity getObjetivoPorId(Integer idObjetivo) {
        return metaDao.buscarObjetivoPorId(idObjetivo);
    }

    public TempoEntity getTempoPorId(Integer idTempo) {
        return metaDao.buscarTempoPorId(idTempo);
    }
}
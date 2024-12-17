package com.fourcamp.NutriPlan.service.diario;

import com.fourcamp.NutriPlan.dao.alimento.RefeicaoDao;
import com.fourcamp.NutriPlan.dao.diario.DiarioDao;
import com.fourcamp.NutriPlan.dtos.diario.DiarioDto;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.model.alimento.RefeicaoEntity;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;
import com.fourcamp.NutriPlan.service.alimento.AlimentoService;
import com.fourcamp.NutriPlan.service.alimento.RefeicaoService;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DiarioService {



    @Autowired
    DiarioDao diarioDao;
    @Autowired
    RefeicaoDao refeicaoDao;
    @Autowired
    ContaService contaService;
    @Autowired
    AlimentoService alimentoService;


    public DiarioEntity salvarDiario(Integer idConta, Integer idRefeicao) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.now();
        String dataStr = data.format(dtf);

        DiarioDto diario = new DiarioDto();
        diario.setFkIdConta(idConta);
        diario.setData(dataStr);
        diario.setFkIdRefeicao(idRefeicao);

        return diarioDao.salvarDiario(diario);

    }

    public String getDiarioListToString(String email) {
        Integer idConta = contaService.getIdContaPorEmail(email);

        List<DiarioEntity> diarioEntityList = getDiarioList(idConta);
        List<String> nomeAlimentoList = new ArrayList<>();
        List<String> refeicaoStrList = new ArrayList<>();
        List<String> diarioToString = new ArrayList<>();
        String diarioStr = "";

        for (DiarioEntity diario : diarioEntityList) {
            int loop = 0;
            for (RefeicaoEntity refeicao : diario.getRefeicao()) {
                if (loop > 0) {

                    break;
                }
                for (AlimentoEntity alimento : refeicao.getAlimentoList()) {
                    nomeAlimentoList.add(alimento.getNome());
                }
                String refeicaoStr = nomeAlimentoList.toString();
                refeicaoStrList.add(refeicaoStr);
                nomeAlimentoList.clear();
                loop++;
            }
            diarioStr = ("(" + diario.getData() + ")" + ":" + refeicaoStrList+"\n");
            diarioToString.add(diarioStr);
            refeicaoStrList.clear();

        }

        return "Seu Diario: \n" +
                "DATA  :  REFEICAO \n" +
                diarioToString;

    }

    public List<DiarioEntity> getDiarioList(Integer idConta) {
        List<DiarioEntity> diarioEntityList = diarioDao.getDiarioList(idConta);

        for (DiarioEntity diario : diarioEntityList) {
            List<RefeicaoEntity> refeicaoList = refeicaoDao.getRefeicao(diario.getFkIdRefeicao());
            diario.setRefeicao(refeicaoList);
            for (RefeicaoEntity refeicao : refeicaoList) {
                List<AlimentoEntity> alimentoEntityList = refeicaoDao.getRefeicaoAlimentoList(refeicao.getFkNrIdRefeicao());
                refeicao.setAlimentoList(alimentoEntityList);
            }
        }

        return diarioEntityList;
    }

    public String alimentoRecomendado(String email) {
        Integer idConta = contaService.getIdContaPorEmail(email);
        List<String> alimentoRecomendado = new ArrayList<>();

        List<DiarioEntity> diarioEntityList =getDiarioList(idConta);

        for (DiarioEntity diario: diarioEntityList)
        {
            Integer loop = 0;
            for (RefeicaoEntity refeicao: diario.getRefeicao())
            {
                if (loop>0)
                {
                    break;
                }
                for (AlimentoEntity alimento: refeicao.getAlimentoList())
                {
                    Integer idAlimento = alimentoService.getIdAlimentoPorNome(alimento.getNome());
                  String categoria = alimento.getCategoriaAlimento();
                  alimentoRecomendado.add(alimentoService.getAlimentoRecomendado(idAlimento,categoria));

                }
                loop++;
            }
        }
        return "Lista de alimentos recomendados baseado em suas refeições: \n"+
                alimentoRecomendado;
    }
}

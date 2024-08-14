package com.fourcamp.NutriPlan.service.meta;

import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.exception.CategoriaException;
import com.fourcamp.NutriPlan.exception.TempoMetaException;
import com.fourcamp.NutriPlan.utils.Arredondamento;
import com.fourcamp.NutriPlan.utils.CalculoIdade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Service
public class MetaService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private CalculoIdade calculoIdade;


    public String acessarPlano(String email, String categoria, String tempoMeta, String genero, double peso, double altura, Date dataNascimento, String categoriaAtividade) {
        double gastoEnergetico = calcularGETSalvar(email, categoria, tempoMeta, genero, peso, altura, dataNascimento, categoriaAtividade);

        String categoriaUpper = categoria.trim().toUpperCase(Locale.ROOT);
        String tempoCategoriaUpper = tempoMeta.trim().toUpperCase(Locale.ROOT);

        double proteinas = 0;
        double carboidratos = 0;
        double gorduras = peso;

        switch (categoriaUpper) {
            case "PERDER PESO":
                switch (tempoCategoriaUpper) {
                    case "RAPIDO":
                        proteinas = 2.5 * peso;
                        break;
                    case "MEDIO":
                        proteinas = 2.2 * peso;
                        break;
                    case "LONGO PRAZO":
                        proteinas = 2.0 * peso;
                        break;
                    default:
                        throw new TempoMetaException();
                }
                break;
            case "MANUTENCAO":
            case "HIPERTROFIA":
                proteinas = calcularProteina(peso);
                break;
            default:
                throw new CategoriaException();
        }

        carboidratos = calcularCarboidratos(gastoEnergetico, proteinas, gorduras);

        String planoNutricional = "Seu plano nutricional: \n" +
                "Calorias necessárias: " + gastoEnergetico + " kcal\n" +
                "Proteínas: " + proteinas + " g\n" +
                "Carboidratos: " + carboidratos + " g\n" +
                "Gorduras: " + gorduras + " g";

        clienteDao.salvarDiario(
                email,
                "Plano Nutricional",
                1,
                Arredondamento.roundToThreeDecimalPlaces(gastoEnergetico),
                Arredondamento.roundToThreeDecimalPlaces(carboidratos),
                Arredondamento.roundToThreeDecimalPlaces(proteinas),
                Arredondamento.roundToThreeDecimalPlaces(gorduras),
                new Date());
        return planoNutricional;
    }



}

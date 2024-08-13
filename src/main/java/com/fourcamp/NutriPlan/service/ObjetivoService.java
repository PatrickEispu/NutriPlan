package com.fourcamp.NutriPlan.service;

import com.fourcamp.NutriPlan.dao.ClienteDao;
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
public class ObjetivoService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private CalculoIdade calculoIdade;

    public double calcularGETSalvar(String email, String categoria, String tempoMeta, String genero, double peso, double altura, Date dataNascimento, String categoriaAtividade) {
        double tmb = calcularTaxaMetabolica(genero, peso, altura, dataNascimento);
        double get = calcularGET(tmb, categoriaAtividade);
        String categoriaUpper = categoria.trim().toUpperCase(Locale.ROOT);
        String tempoCategoriaUpper = tempoMeta.trim().toUpperCase(Locale.ROOT);

        switch (categoriaUpper) {
            case "PERDER PESO":
                if ("RAPIDO".equals(tempoCategoriaUpper)) {
                    get -= 1000;
                } else if ("MEDIO".equals(tempoCategoriaUpper)) {
                    get -= 600;
                } else if ("LONGO PRAZO".equals(tempoCategoriaUpper)) {
                    get -= 400;
                }
                break;
            case "MANUTENCAO":
                // Mantém o GET como está
                break;
            case "HIPERTROFIA":
                if ("RAPIDO".equals(tempoCategoriaUpper)) {
                    get += 800;
                } else if ("MEDIO".equals(tempoCategoriaUpper)) {
                    get += 500;
                } else if ("LONGO PRAZO".equals(tempoCategoriaUpper)) {
                    get += 300;
                }
                break;
            default:
                throw new CategoriaException();
        }

        clienteDao.salvarTMBGET(email, tmb, get);
        return get;
    }

    private double calcularTaxaMetabolica(String genero, double peso, double altura, Date dataNascimento) {
        double calculo = 0;

        LocalDate nascimentoLocalDate = calculoIdade.convertToLocalDate(dataNascimento);
        int idade = calculoIdade.calcularIdade(nascimentoLocalDate);

        if (Objects.equals(genero, "M")) {
            calculo = 66.5 + (13.75 * peso) + (5.003 * (altura * 100)) - (6.75 * idade);
        } else if (Objects.equals(genero, "F")) {
            calculo = 655.1 + (9.563 * peso) + (1.850 * (altura * 100)) - (4.676 * idade);
        }

        return calculo;
    }

    private double calcularGET(double tmb, String categoriaAtividade) {
        double fatorAtividade;

        switch (categoriaAtividade) {
            case "nao_muito_ativo":
                fatorAtividade = 1.2;
                break;
            case "levemente_ativo":
                fatorAtividade = 1.375;
                break;
            case "ativo":
                fatorAtividade = 1.55;
                break;
            case "bastante_ativo":
                fatorAtividade = 1.725;
                break;
            default:
                throw new UnsupportedOperationException("Categoria de atividade desconhecida: " + categoriaAtividade);
        }

        return tmb * fatorAtividade;
    }

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

    private double calcularProteina(double peso) {
        return 2.0 * peso;
    }

    private double calcularCarboidratos(double gastoEnergetico, double proteinas, double gorduras) {
        double proteinasEmCalorias = proteinas * 4;
        double gordurasEmCalorias = gorduras * 9;
        return (gastoEnergetico - proteinasEmCalorias - gordurasEmCalorias) / 4;
    }

}

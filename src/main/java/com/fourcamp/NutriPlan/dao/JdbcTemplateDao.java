package com.fourcamp.NutriPlan.dao;

import com.fourcamp.NutriPlan.dao.impl.JdbcTemplateDaoImpl;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.model.Alimento;
import com.fourcamp.NutriPlan.model.Cliente;
import com.fourcamp.NutriPlan.model.Diario;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JdbcTemplateDao {

    void criarCliente(String nome, String email, String genero, double peso, double pesoDesejado, double altura, Date dataNascimento, String senha, String categoria, String tempoMeta);

    Cliente buscarClientePorEmail(String email);

    void atualizarPesoCliente(String email, double novoPeso);

    void formularioObjetivo(String email, String categoria, String tempoMeta);

    Map<String, Object> buscarDadosCliente(String email);

    void salvarTMBGET(String email, double tmb, double get);

    void criarAlimento(Double kcal, Double carboidrato, Double proteina, Double gordura, Double quantidade, String nome);

    List<Alimento> listarAlimentos();

    Alimento buscarAlimentoPorNome(String nome);

    void salvarDiario(String email, String alimento, double quantidade, double kcal, double carboidrato, double proteina, double gordura, Date data);

    List<Diario> buscarPlanoCliente(String email);

}

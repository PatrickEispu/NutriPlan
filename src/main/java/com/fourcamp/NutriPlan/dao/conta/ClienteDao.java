package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.Cliente;

import java.util.Date;
import java.util.Map;

public interface ClienteDao {

    void criarCliente(String nome, String email, String genero, double peso, double pesoDesejado, double altura, Date dataNascimento, String senha, String categoria, String tempoMeta);

    Cliente buscarClientePorEmail(String email);

    void atualizarPesoCliente(String email, double novoPeso);

    void formularioObjetivo(String email, String categoria, String tempoMeta);

    Map<String, Object> buscarDadosCliente(String email);

    void salvarTMBGET(String email, double tmb, double get);



    void salvarDiario(String email, String alimento, double quantidade, double kcal, double carboidrato, double proteina, double gordura, Date data);

    //List<Diario> buscarPlanoCliente(String email);

}

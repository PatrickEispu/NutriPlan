package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dto.conta.ClientePrimeiroAcessoDto;
import com.fourcamp.NutriPlan.model.conta.Cliente;

import java.util.List;

public interface ClienteDao {
    String criarCliente(ClientePrimeiroAcessoDto cliente);
    Cliente buscarClientePorId(int idConta);
    List<Cliente> buscarTodosClientes();
    void atualizarCliente(Cliente cliente);

    String buscarClienteCategoria(int idCategoria);

    void atualizarTMBGET(Integer idConta, double tmb, double get);
}
package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dto.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.Cliente;

import java.util.List;

public interface ClienteDao {
    String criarCliente(Cliente cliente);
    Cliente buscarClientePorId(int idConta);
    List<Cliente> buscarTodosClientes();
    void atualizarCliente(Cliente cliente);
}
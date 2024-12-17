package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;

import java.util.List;

public interface ClienteDao {
    ClienteEntity criarConta(ClienteEntity cliente);
    ClienteEntity buscarClientePorId(int idConta);
    List<ClienteEntity> buscarTodosClientes();
    void atualizarCliente(ClienteDto cliente);

    String buscarClienteCategoria(int idCategoria);

    void atualizarTMBGET(Integer idConta, double tmb, double get);
}

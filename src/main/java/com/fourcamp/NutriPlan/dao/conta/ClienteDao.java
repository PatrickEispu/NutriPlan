package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dto.conta.ClientePrimeiroAcessoDto;
import com.fourcamp.NutriPlan.dto.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;

import java.util.List;

public interface ClienteDao {
    ClienteEntity criarConta(ClienteEntity cliente);
    ClienteEntity buscarClientePorId(int idConta);
    List<ClienteEntity> buscarTodosClientes();
    void atualizarCliente(ClienteEntity cliente);
}

//public interface ClienteDao {
//    String criarCliente(ClientePrimeiroAcessoDto cliente);
//    Cliente buscarClientePorId(int idConta);
//    List<Cliente> buscarTodosClientes();
//    void atualizarCliente(Cliente cliente);
//
//    String buscarClienteCategoria(int idCategoria);
//
//    void atualizarTMBGET(Integer idConta, double tmb, double get);
//}
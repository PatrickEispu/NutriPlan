package com.fourcamp.NutriPlan.service.conta;
import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.model.conta.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    public Cliente criarCliente(Cliente cliente){
        clienteDao.criarCliente(cliente);
        return cliente;
    }

    public Cliente buscarClientePorId(int idConta) {
        return clienteDao.buscarClientePorId(idConta);
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteDao.buscarTodosClientes();
    }

    public void atualizarCliente(Cliente cliente) {
        clienteDao.atualizarCliente(cliente);
    }

}

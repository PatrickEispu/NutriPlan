package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dto.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.Cliente;

public interface ClienteDao {
    String criarCliente(Cliente cliente);
}
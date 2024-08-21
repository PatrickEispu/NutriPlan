package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dao.mapper.ClienteRowMapper;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Repository
public class ClienteDaoImpl implements ClienteDao {
    
    private static final String ATUALIZAR_CLIENTE = "SELECT public.atualizar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteEntity criarConta(ClienteEntity cliente) {
        String sql = "SELECT criar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.queryForObject(sql, new Object[]{
                        cliente.getGenero(), cliente.getPeso(), cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet(), cliente.getIdCategoria(), cliente.getIdConta()},
                Integer.class);
        return cliente;
    }

    @Override
    @Transactional
    public ClienteEntity buscarClientePorId(int idConta) {
        String sql = "SELECT * FROM cliente WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idConta}, new ClienteRowMapper());
    }

    @Override
    @Transactional
    public List<ClienteEntity> buscarTodosClientes() {
        String sql = "SELECT * FROM listar_todos_clientes()";
        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }


    @Override
    @Transactional
    public String atualizarCliente(ClienteEntity cliente) {
        try {
            String sql = ATUALIZAR_CLIENTE;
            jdbcTemplate.update(sql, cliente.getIdConta(), String.valueOf(cliente.getGenero()), cliente.getPeso(), cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet(), cliente.getIdCategoria());
            return "Cliente " + cliente.getNome() + " atualizado com sucesso";
        } catch (Exception e) {
            return "Erro ao atualizar o cliente: " + e.getMessage();
        }
    }
}

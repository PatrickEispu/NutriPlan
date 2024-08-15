package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class ClienteDaoImpl implements ClienteDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void criarCliente(Cliente cliente) {
        String sql = "CALL criar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {cliente.getIdConta(), cliente.getGenero(), cliente.getPeso(),cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet() , cliente.getIdCategoria()};
        int[] types = {Types.NUMERIC, Types.CHAR, Types.NUMERIC, Types.NUMERIC, Types.DATE, Types.NUMERIC, Types.NUMERIC, Types.VARCHAR , Types.NUMERIC};

        jdbcTemplate.update(sql, params, types);
    }
}

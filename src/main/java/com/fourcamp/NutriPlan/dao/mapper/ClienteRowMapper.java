package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<ClienteEntity> {

    @Override
    public ClienteEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdConta(rs.getInt(1));
        cliente.setGenero(rs.getString(2));
        cliente.setPeso(rs.getDouble(3));
        cliente.setAltura(rs.getDouble(4));
        cliente.setDataNascimento(rs.getString(5));
        cliente.setTmb(rs.getDouble(6));
        cliente.setGet(rs.getDouble(7));
        cliente.setIdCategoria(rs.getInt(8));
        return cliente;
    }
}

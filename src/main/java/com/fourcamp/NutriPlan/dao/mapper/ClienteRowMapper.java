package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<ClienteEntity> {

    //Metodo para listagem de cliente
    @Override
    public ClienteEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setFkNrIdConta(rs.getInt(1));
        cliente.setNome(rs.getString(2));
        cliente.setEmail(rs.getString(3));
        cliente.setSenha(rs.getString(4));
        cliente.setDsGenero(rs.getString(5));
        cliente.setNrPeso(rs.getDouble(6));
        cliente.setNrAltura(rs.getDouble(7));
        cliente.setDsDataNascimento(rs.getString(8));
        cliente.setNrTmb(rs.getDouble(9));
        cliente.setNrGet(rs.getDouble(10));
        cliente.setFkNrIdCategoria(rs.getString(11));
        cliente.setTipoConta(rs.getString(12));
        return cliente;
    }
}

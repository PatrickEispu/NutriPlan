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
        cliente.setIdConta(rs.getInt(1));
        cliente.setNome(rs.getString(2));
        cliente.setEmail(rs.getString(3));
        cliente.setSenha(rs.getString(4));
        cliente.setGenero(rs.getString(5));
        cliente.setPeso(rs.getDouble(6));
        cliente.setAltura(rs.getDouble(7));
        cliente.setDataNascimento(rs.getString(8));
        cliente.setTmb(rs.getDouble(9));
        cliente.setGet(rs.getDouble(10));
        cliente.setCategoria(rs.getString(11));
        cliente.setTipoConta(rs.getString(12));
        return cliente;
    }
}

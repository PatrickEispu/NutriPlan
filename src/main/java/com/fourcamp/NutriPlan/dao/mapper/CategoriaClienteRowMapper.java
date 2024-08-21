package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.conta.CategoriaClienteEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaClienteRowMapper implements RowMapper<CategoriaClienteEntity> {

    @Override
    public CategoriaClienteEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoriaClienteEntity categoriaCliente = new CategoriaClienteEntity();
        categoriaCliente.setIdCategoria(rs.getInt(1));
        categoriaCliente.setNomeCategoria(rs.getString(2));
        return categoriaCliente;
    }
}

package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaAlimentoRowMapper implements RowMapper<CategoriaAlimentoEntity> {

    @Override
    public CategoriaAlimentoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoriaAlimentoEntity categoria = new CategoriaAlimentoEntity();
        categoria.setIdCategoriaAlimento(rs.getInt(1));
        categoria.setNomeCategoria(rs.getString(2));
        return categoria;
    }
}

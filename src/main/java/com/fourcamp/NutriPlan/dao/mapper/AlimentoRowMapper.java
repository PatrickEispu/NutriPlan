package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class AlimentoRowMapper implements RowMapper<AlimentoEntity> {

    @Override
    public AlimentoEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
        AlimentoEntity alimento = new AlimentoEntity();
        alimento.setIdAlimento(rs.getInt(1));
        alimento.setIdCategoriaAlimento(rs.getInt(2));
        alimento.setKcal(rs.getDouble(3));
        alimento.setCarboidrato(rs.getDouble(4));
        alimento.setProteina(rs.getDouble(5));
        alimento.setGordura(rs.getDouble(6));
        alimento.setQuantidade(rs.getDouble(7));
        alimento.setNome(rs.getString(8));
        return alimento;
    }
}

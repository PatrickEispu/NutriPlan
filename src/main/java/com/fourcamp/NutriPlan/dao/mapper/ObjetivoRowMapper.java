package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjetivoRowMapper implements RowMapper<ObjetivoEntity> {

    @Override
    public ObjetivoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ObjetivoEntity objetivo = new ObjetivoEntity();
        objetivo.setNrIdObjetivo(rs.getInt(1));
        objetivo.setDsDescricaoObjetivo(rs.getString(2));
        return objetivo;
    }
}

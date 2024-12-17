package com.fourcamp.NutriPlan.dao.mapper;

import com.fourcamp.NutriPlan.model.meta.TempoEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TempoRowMapper implements RowMapper<TempoEntity> {

    @Override
    public TempoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TempoEntity tempo = new TempoEntity();
        tempo.setIdTempo(rs.getInt(1));
        tempo.setDescricaoTempo(rs.getString(2));
        return tempo;
    }
}

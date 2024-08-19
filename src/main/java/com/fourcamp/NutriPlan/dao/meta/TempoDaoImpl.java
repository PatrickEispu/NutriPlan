package com.fourcamp.NutriPlan.dao.meta;

import com.fourcamp.NutriPlan.dao.mapper.TempoRowMapper;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TempoDaoImpl implements TempoDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TempoEntity buscarObjetivoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_tempo_por_descricao(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new TempoRowMapper());
    }
}

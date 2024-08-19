package com.fourcamp.NutriPlan.dao.meta;

import com.fourcamp.NutriPlan.dao.mapper.ObjetivoRowMapper;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ObjetivoDaoImpl implements ObjetivoDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ObjetivoEntity buscarObjetivoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_objetivo_por_descricao(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new ObjetivoRowMapper());
    }
}

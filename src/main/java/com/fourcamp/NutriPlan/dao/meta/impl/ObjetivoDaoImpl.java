package com.fourcamp.NutriPlan.dao.meta.impl;

import com.fourcamp.NutriPlan.dao.mapper.ObjetivoRowMapper;
import com.fourcamp.NutriPlan.dao.meta.ObjetivoDao;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ObjetivoDaoImpl implements ObjetivoDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ObjetivoEntity buscarObjetivoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_objetivo_por_descricao(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new ObjetivoRowMapper());
    }
}

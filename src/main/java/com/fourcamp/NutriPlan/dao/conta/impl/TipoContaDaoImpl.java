package com.fourcamp.NutriPlan.dao.conta.impl;

import com.fourcamp.NutriPlan.dao.conta.TipoContaDao;
import com.fourcamp.NutriPlan.dao.mapper.TipoContaRowMapper;
import com.fourcamp.NutriPlan.model.conta.TipoContaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TipoContaDaoImpl implements TipoContaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TipoContaEntity buscarTipoContaPorNome(String nome) {
        String sql = "SELECT * FROM buscar_tipo_conta_por_nome(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new TipoContaRowMapper());
    }
}

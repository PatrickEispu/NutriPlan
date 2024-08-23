package com.fourcamp.NutriPlan.dao.alimento.impl;

import com.fourcamp.NutriPlan.dao.alimento.DispensaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DispensaDaoImpl implements DispensaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer quantidade) {
        String sql = "SELECT adicionar_alimento_na_dispensa(?,?,?)";
        jdbcTemplate.queryForObject(sql, new Object[] {idDispensa,idAlimento,quantidade}, Integer.class);
    }

    public Integer criarDispensa(Integer idConta) {
        String sql = "SELECT criar_dispensa(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{idConta}, Integer.class);

    }
}

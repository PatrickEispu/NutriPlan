package com.fourcamp.NutriPlan.dao.conta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContaDaoImpl implements ContaDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Integer getIdContaPorEmail(String email) {
       String sql=("SELECT buscar_id_conta_por_email(?)");
       return jdbcTemplate.queryForObject(sql, Integer.class,email) ;
    }
}
